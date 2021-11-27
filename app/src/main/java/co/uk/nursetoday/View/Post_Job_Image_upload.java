package co.uk.nursetoday.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import co.uk.nursetoday.R;

public class Post_Job_Image_upload extends AppCompatActivity {

    LottieAnimationView image_selection;
    private TextView user, logOut;
    private ImageView imageAgency, imageViewLottie;
    private Button btn_next, btn_upload, btn_noimage;
    private Uri imageUri; // to save the Image URI in a global variable so we can use it in other methods
    private StorageReference sref; //to refer to our Firebase Storage Location
    private FirebaseAuth fbAuth;
    Uri image_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job_image_upload);


        image_selection = findViewById(R.id.image_selection_postJob);
        imageViewLottie = findViewById(R.id.iv_lottie);
       // imageAgency = findViewById(R.id.iv_img_postJob);
        user = findViewById(R.id.tv_user_postJob);
        logOut = findViewById(R.id.tv_logout_postJob);
        btn_upload = findViewById(R.id.btn_upload_postJob);
        btn_noimage = findViewById(R.id.btn_noimage_postJob);
        sref = FirebaseStorage.getInstance().getReference("Job_Vacancies"); //to refer to FB storage
       // fbAuth = FirebaseAuth.getInstance();
       // user.setText(Session.LiveSession.user.getFirst_name()); //displays the user currently logged in


        image_selection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setType("image/*"); // only select images, filter out all files
                i.setAction(Intent.ACTION_GET_CONTENT); // tell the intent to bring in selected content (image)
               startActivityForResult(i,105);
            }
        });

/*
        imageAgency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setType("image/*"); // only select images, filter out all files
                i.setAction(Intent.ACTION_GET_CONTENT); // tell the intent to bring in selected content (image)
                startActivityForResult(i,105);
            }
        });
*/

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("Job_Vacancies");
                String jobId = dbref.push().getKey(); //get Id to use as image name so no two are the same
                StorageReference reference = sref.child(jobId +"."+getExt(image_path));
                reference.putFile(image_path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String url = uri.toString();
                                Intent i = new Intent(Post_Job_Image_upload.this, Post_Job2.class);
                                i.putExtra("url", url);
                                i.putExtra("id", jobId);
                                startActivity(i);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                reference.delete();
                                Toast.makeText(Post_Job_Image_upload.this, "Image failed to upload", Toast.LENGTH_LONG).show();
                                        //additionally, can restart the acxtivity so the user tries again
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        reference.delete();
                        Toast.makeText(Post_Job_Image_upload.this, "Image failed to upload", Toast.LENGTH_LONG).show();
                        //additionally, can restart the acxtivity so the user tries again
                    }
                });

            }
        });

        btn_noimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("Job_Vacancies");
                final StorageReference reference = sref.child("job.png");
                final String jobId = dbref.push().getKey();
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        final String url = uri.toString();
                        Intent i = new Intent(Post_Job_Image_upload.this, Post_Job2.class);
                        i.putExtra("url", url);
                        i.putExtra("id", jobId);
                        startActivity(i);
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==105 && resultCode == RESULT_OK && data.getData()!=null)
        {
            image_selection.setVisibility(View.INVISIBLE);
            imageViewLottie.setVisibility(View.VISIBLE);
            btn_upload.setVisibility(View.VISIBLE);
            //load the image into our Image view
            Picasso.get().load(data.getData()).fit().into(imageViewLottie);
            image_path = data.getData();
        }
    }

    private String getExt(Uri path)
    {
        ContentResolver resolver = getContentResolver(); //strips the extension from the image
        MimeTypeMap map = MimeTypeMap.getSingleton();
        return map.getExtensionFromMimeType(resolver.getType(path)); // returns the extension as a String
    }
}