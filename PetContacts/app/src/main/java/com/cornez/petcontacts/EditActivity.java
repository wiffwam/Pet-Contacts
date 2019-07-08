package com.cornez.petcontacts;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class EditActivity extends Activity {

    //Pet Edit Data Screen
    ImageView editPhoto;
    EditText editName;
    EditText editDetails;
    EditText editpNumber;
    Button editContactBTN;

    //Variables to Store the New Data
    Uri photo;
    int current_id;
    int position;
    String c_name;
    String c_detail;
    String c_Phone;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_item);

        //REFERENCE INPUT UI COMPONENTS FROM THE LAYOUT
        editContactBTN = (Button) findViewById(R.id.button);
        editName = (EditText) findViewById(R.id.edit_name);
        editDetails = (EditText) findViewById(R.id.edit_details);
        editpNumber = (EditText)
                findViewById(R.id.edit_pNumber);
        editPhoto = (ImageView) findViewById(R.id.editPhoto);

        //ENABLE THE UPDATE BUTTON
        editContactBTN.setEnabled(true);

        //SET THE ONCLICK LISTENER FOR THE UPDATE PHOTO ID IMAGE VIEW
        editPhoto.setOnClickListener(getPhotoFromGallery);


        //CALL THE GET CURRENT CONTACT METHOD
        getCurrentContact();

    }

    private final View.OnClickListener getPhotoFromGallery =
            new View.OnClickListener() {

                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);

                    startActivityForResult(
                            Intent.createChooser(intent,
                                    "Select Contact Image"), 3);
                }
            };

    private void getCurrentContact(){

        Intent i = getIntent();
        Bundle b = i.getExtras();
        photo = Uri.parse(i.getStringExtra("Photo"));
        current_id = b.getInt("ID");
        c_name = b.getString("Name");
        c_detail = b.getString("Details");
        c_Phone = b.getString("Phone");
        position = b.getInt("Position");
        editName.setText(c_name);
        editDetails.setText(c_detail);
        editpNumber.setText(c_Phone);
        editPhoto.setImageURI(photo);
        editContactBTN.setEnabled(true);

    }

    public void onClick(View view) {

        Intent editContact = new Intent(this, MainActivity.class);

        editContact.putExtra("ID", current_id);
        editContact.putExtra("Name", editName.getText().toString());
        editContact.putExtra("Details", editDetails.getText().toString());
        editContact.putExtra("Phone", editpNumber.getText().toString());
        editContact.putExtra("Photo", photo.toString());
        editContact.putExtra("Position", position);

        setResult(2, editContact);

        finish();
    }

    // INTENT RETURNS A PHOTO SELECTED FROM THE PHOTO GALLERY
    public void onActivityResult(int reqCode,
                                 int resCode, Intent data) {
        if (resCode == RESULT_OK) {
            if (reqCode == 3) {
                photo = data.getData();
                editPhoto.setImageURI(data.getData());
            }
        }
    }


}
