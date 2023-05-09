package sg.edu.rp.c346.id22015131.billplease;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    EditText amtInput;
    EditText paxInput;
    EditText discountInput;
    RadioGroup paymentTypes;
    TextView totalView;
    TextView splitView;
    ToggleButton svsButton;
    ToggleButton gstButton;
    Button splitButton;
    Button resetButton;

    boolean isFieldsChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        amtInput = findViewById(R.id.idAmount);
        paxInput = findViewById(R.id.idPax);
        discountInput = findViewById(R.id.idDiscount);
        paymentTypes = findViewById(R.id.idPayment);
        totalView = findViewById(R.id.idTotalBill);
        splitView = findViewById(R.id.idSplitView);
        svsButton = findViewById(R.id.idSvs);
        gstButton = findViewById(R.id.idGst);
        splitButton = findViewById(R.id.idSplit);
        resetButton = findViewById(R.id.idReset);

        splitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isFieldsChecked = checkFields();
                if (isFieldsChecked) {
                    double disc = Integer.parseInt(discountInput.getText().toString());
                    int pax = Integer.parseInt(paxInput.getText().toString());
                    double amt = Integer.parseInt(amtInput.getText().toString());
                    String output1 = "";
                    String output2 = "";
                    double finalAmt = 0;

                    double convertDisc = disc / 100;
                    double base = 1 - convertDisc;

                    if (svsButton.isChecked()) {
                        base += 0.1;
                    }
                    if (gstButton.isChecked()) {
                        base += 0.08;
                    }

                    finalAmt = amt * base;
                    double split = finalAmt / pax;

                    output1 = String.format("Total Bill: $%.2f", finalAmt);
                    if (paymentTypes.getCheckedRadioButtonId() == R.id.idCash) {
                        output2 = String.format("Each Pays: $%.2f in cash", split);
                    } else if (paymentTypes.getCheckedRadioButtonId() == R.id.idPayNow) {
                        output2 = String.format("Each Pays: $%.2f via PayNow to 912345678", split);
                    }

                    totalView.setText(output1);
                    splitView.setText(output2);
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (svsButton.isChecked()) {
                    svsButton.setChecked(false);
                }
                if (gstButton.isChecked()) {
                    gstButton.setChecked(false);
                }
                amtInput.getText().clear();
                paxInput.getText().clear();
                discountInput.getText().clear();
                totalView.setText("Total Bill $0");
                splitView.setText("Each Pays: $0 in cash");
            }
        });
    }

    private boolean checkFields() {
        if (TextUtils.isEmpty(amtInput.getText().toString())) {
            amtInput.setError("Amount is required");
            return false;
        } else if (Integer.parseInt(amtInput.getText().toString()) < 0) {
            amtInput.setError("Enter amount more than 0");
            return false;
        } else if (Integer.parseInt(amtInput.getText().toString()) > 2000) {
            amtInput.setError("Enter amount lesser than 2001");
            return false;
        } else if (TextUtils.isEmpty(paxInput.getText().toString())) {
            paxInput.setError("Pax is required");
            return false;
        } else if (Integer.parseInt(paxInput.getText().toString()) <= 0) {
            paxInput.setError("Enter pax more than 0");
            return false;
        } else if (Integer.parseInt(paxInput.getText().toString()) > 30) {
            paxInput.setError("Enter pax lesser than 31");
            return false;
        } else if (TextUtils.isEmpty(discountInput.getText().toString())) {
            discountInput.setError("Discount is required");
            return false;
        } else if (Integer.parseInt(discountInput.getText().toString()) > 100) {
            discountInput.setError("Enter valid discount (0-100)");
            return false;
        }
        return true;
    }

    ;
}