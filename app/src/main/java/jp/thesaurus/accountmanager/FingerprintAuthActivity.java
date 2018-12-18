package jp.thesaurus.accountmanager;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FingerprintAuthActivity extends AppCompatActivity {

    private static final String TAG = "FingerprintAuthActivity";
    TextView errorTextView;
    ImageView fingerprintImageView;
    Animation shake;
    //private AlertDialog.Builder builder;
    //private Dialog mDialog;
    //private CancellationSignal mCancellationSignal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprint_auth);

        FingerprintManager fingerprintManager =
                (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
        try {
            if (fingerprintManager.isHardwareDetected() && fingerprintManager.hasEnrolledFingerprints()) {
/*
                ImageView iv = new ImageView(this);
                iv.setImageResource(R.mipmap.settings_fingerprint);
                iv.setAdjustViewBounds(true);

                builder = new AlertDialog.Builder(this);
                builder.setTitle("指紋認証");
                builder.setMessage("指紋センサーに触れてください");
                builder.setView(iv);
                builder.setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mCancellationSignal.cancel();
                    }
                });
                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        mCancellationSignal.cancel();
                    }
                });*/
                //mDialog = builder.show();
                //mCancellationSignal = new CancellationSignal();

                fingerprintManager.authenticate(null, null, 0, new FingerprintManager.AuthenticationCallback() {
                            // エラー
                            @Override
                            public void onAuthenticationError(
                                    int errorCode,
                                    CharSequence errString) {
                                Log.e(TAG, "ERROR:" + errorCode + ":" + errString);
                                errorTextView = findViewById(R.id.title_text_view);
                                errorTextView.setText("ERROR:" + errString);
                                Toast.makeText(FingerprintAuthActivity.this, errString, Toast.LENGTH_LONG).show();
                                //mDialog.dismiss();
                            }
                            // 指紋認証失敗
                            @Override
                            public void onAuthenticationFailed() {
                                Log.i(TAG, "Failed 指紋認証失敗");
                                errorTextView = findViewById(R.id.title_text_view);
                                errorTextView.setText("もう一度指紋センサーに触れてください");
                                shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                                errorTextView.startAnimation(shake);

                                fingerprintImageView = findViewById(R.id.fingerprint_image_view);
                                fingerprintImageView.startAnimation(shake);
                            }
                            // 指紋認証成功
                            @Override
                            public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                                //mDialog.dismiss();
                                errorTextView = findViewById(R.id.title_text_view);
                                errorTextView.setText("認証OK");
                                errorTextView.setTextColor(Color.parseColor("#00A95F"));
                                Intent intent = new Intent(FingerprintAuthActivity.this, AccountListActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }, null);
            }
        } catch (SecurityException secEx) {
            secEx.printStackTrace();
        }
    }
}
