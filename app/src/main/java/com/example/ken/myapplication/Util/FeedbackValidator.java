package com.example.ken.myapplication.Util;



import android.app.AlertDialog;
import android.content.Context;

import com.example.ken.myapplication.R;



public class FeedbackValidator {

    private Context context;

    public FeedbackValidator(Context context) {
        this.context = context;
    }

    public boolean validate(float rating, String firstName, String lastName, String emailadres, String message) {

        //Errors
        String errors = "";

        if (firstName.length() == 0)

            errors += context.getString(R.string.firstname_notfilled) + "\n";

        if (lastName.length() == 0)

            errors += context.getString(R.string.lastname_notfilled) + "\n";

        if (emailadres.length() == 0)

            errors += context.getString(R.string.emailadres_notfilled) + "\n";

        if (message.length() == 0)

            errors += context.getString(R.string.message_notfilled) + "\n";

        if (errors.length() == 0) {


            return true;

        } else {

            errors += "\n" + context.getString(R.string.improve_feedback);

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(context.getString(R.string.feedback_error_title));
            builder.setMessage(errors);
            builder.setPositiveButton(context.getString(R.string.ok), null);
            AlertDialog dialog = builder.create();
            dialog.show();
            return false;
        }
    }
}
