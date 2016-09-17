package com.gashfara.it.gsapp;

import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.kii.cloud.storage.Kii;
import com.kii.cloud.storage.KiiBucket;
import com.kii.cloud.storage.KiiObject;
import com.kii.cloud.storage.callback.KiiObjectCallBack;
import com.kii.cloud.storage.callback.KiiObjectPublishCallback;
import com.kii.cloud.storage.exception.CloudExecutionException;
import com.kii.cloud.storage.resumabletransfer.KiiRTransfer;
import com.kii.cloud.storage.resumabletransfer.KiiRTransferCallback;
import com.kii.cloud.storage.resumabletransfer.KiiUploader;

import java.io.File;
import java.io.FileOutputStream;


public class PostActivity extends ActionBarActivity {
    //����g�p����C���e���g�̌��ʂ̔ԍ��B�K���Ȓl��OK.
    private static final int IMAGE_CHOOSER_RESULTCODE = 1;
    //�摜�̃p�X��ۑ����Ă���
    private String mImagePath = null;
    //UP�����摜��KiiObject
    private KiiObject mKiiImageObject = null;
    //���͂����R�����g
    private String comment;
    //�J�����ŎB�e�����摜��uri
    private Uri mImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        //�摜�{�^���ɃN���b�N�C�x���g��ǉ����Ă��܂��B
        Button attachBtn = (Button) findViewById(R.id.attach_button);
        attachBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //�N���b�N�������͉摜�I��
                onAttachFileButtonClicked(v);
            }
        });
        //�J�����{�^���ɃN���b�N�C�x���g��ǉ����Ă��܂��B
        Button attachCameraBtn = (Button) findViewById(R.id.attach_camera_button);
        attachCameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //�N���b�N�������̓J�����N������
                onAttachCameraFileButtonClicked(v);
            }
        });
        //���e�{�^���ɃN���b�N�C�x���g��ǉ����Ă��܂��B
        Button postBtn = (Button) findViewById(R.id.post_button);
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //�N���b�N�������͓��e����
                onPostButtonClicked(v);
            }
        });
    }
    //�摜�̓Y�t�{�^�������������̏���
    public void onAttachFileButtonClicked(View v) {
        //�M�������[���J���C���e���g���쐬���ċN������B
        Intent intent = new Intent();
        //�t�A�C���̃^�C�v��ݒ�
        intent.setType("image/*");
        //�摜�̃C���e���g
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //Activity���N��
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMAGE_CHOOSER_RESULTCODE);
    }
    //�J�����̓Y�t�{�^�������������̏���
    public void onAttachCameraFileButtonClicked(View v) {
        //�J�����͋@��ˑ����傫���A���낢��T���v���������ق����ǂ�
        //�R�����g��Xperia�p�ɍ�������́B�s�v�B
        //�J�����̃C���e���g���쐬
        //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //Activity���N��
        //startActivityForResult(Intent.createChooser(intent, "Camera"), IMAGE_CHOOSER_RESULTCODE);
        //���ݎ��������ƂɈꎞ�t�@�C�������쐬
        String filename = System.currentTimeMillis() + ".jpg";
        //�ݒ��ۑ�����p�����[�^���쐬
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, filename);//�t�@�C����
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");//�t�@�C���̎��
        //�ݒ肵���ꎞ�t�@�C�����쐬
        mImageUri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        //�J�����̃C���e���g���쐬
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//�J����
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);//�摜�̕ۑ���
        //�C���e���g�N��
        startActivityForResult(intent, IMAGE_CHOOSER_RESULTCODE);
    }
    //�摜��I��������Ɏ��s�����R�[���o�b�N�֐��B�C���e���g�̎��s���ꂽ��ɃR�[���o�b�N�����B�����I�Ɏ��s����܂��B
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //���̃C���e���g�̎��s���ʂƋ�ʂ��邽��startActivity�Ŏw�肵���萔IMAGE_CHOOSER_RESULTCODE�ƈ�v���邩�m�F
        if (requestCode == IMAGE_CHOOSER_RESULTCODE) {
            //���s�̎�
            if (resultCode != RESULT_OK ) {
                return;
            }

            //�摜���擾����BXperia�̏ꍇ��data�ɉ摜�������Ă���B����ȊO��intent�Őݒ肵��mImageUri�ɓ����Ă���B
            Uri result;
            if(data != null) {
                result = data.getData();
            }else {
                result = mImageUri;
                Log.d("mogi:mImageUri:", result.toString());
            }
            //��ʂɉ摜��\��
            ImageView iv = (ImageView) findViewById(R.id.image_view1);
            iv.setImageURI(result);


            //�摜�̃p�X��ݒ�BUpload�ł����B
            mImagePath = getFilePathByUri(result);

        }
    }
    //uri����t�@�C���̃p�X���擾����B�o�[�W�����ɂ���ď������Ⴄ�BKiiCloud�̃`���[�g���A�������荞�񂾁B�ėp�I�Ɏg���܂��B
    private String getFilePathByUri(Uri selectedFileUri) {
        //4.2�ȍ~�̎�
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            // Workaround of retrieving file image through ContentResolver
            // for Android4.2 or later
            String filePath = null;
            FileOutputStream fos = null;
            try {
                //�r�b�g�}�b�v���擾
                Bitmap bmp = MediaStore.Images.Media.getBitmap(
                        this.getContentResolver(), selectedFileUri);
                //�ꎞ�ۑ�����f�B���N�g���B�A�v���ɉ�����gsapp�̕�����ύX�����ق����ǂ�
                String cacheDir = Environment.getExternalStorageDirectory()
                        .getAbsolutePath() + File.separator + "gsapp";
                //�f�B���N�g���쐬
                File createDir = new File(cacheDir);
                if (!createDir.exists()) {
                    createDir.mkdir();
                }
                //�ꎞ�t�@�C�������쐬�B����㏑��
                filePath = cacheDir + File.separator + "upload.jpg";
                File file = new File(filePath);
                //�r�b�g�}�b�v��jpg�ɕϊ����Ĉꎞ�I�ɕۑ�����B
                fos = new FileOutputStream(file);
                bmp.compress(Bitmap.CompressFormat.JPEG, 95, fos);
                fos.flush();
                fos.getFD().sync();
            } catch (Exception e) {
                filePath = null;
            } finally {//���Ȃ炸�Ō�Ɏ��s���鏈��
                if (fos != null) {
                    try {
                        //�t�@�C�������
                        fos.close();
                    } catch (Exception e) {
                        // Nothing to do
                    }
                }
            }
            return filePath;
        } else {
            //�f�[�^����T��
            String[] filePathColumn = { MediaStore.MediaColumns.DATA };
            Cursor cursor = this.getContentResolver().query(
                    selectedFileUri, filePathColumn, null, null, null);

            if (cursor == null)
                return null;
            try {
                if (!cursor.moveToFirst())
                    return null;
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                if (columnIndex < 0) {
                    return null;
                }
                //���ꂪ�t�@�C���̃p�X
                String picturePath = cursor.getString(columnIndex);
                return picturePath;
            } finally {
                cursor.close();
            }
        }
    }


    //���e�{�^�����䂵�����̏���
    public void onPostButtonClicked(View v) {
        //���͕����𓾂�
        EditText mCommentField = (EditText) (findViewById(R.id.comment_field));
        comment = mCommentField.getText().toString();
        //Log.d("mogi comment", ":" + comment + ":");
        //�����͂̎��̓G���[.""�͕�������
        if (comment.equals("")) {
            //�_�C�A���O��\��
            showAlert(getString(R.string.no_data_message));
            return;
        }
        //�摜��UP���Ă���messages�ɓ��e�B
        if (mImagePath != null) {
            //�t�@�C����UP�A������������postMessages�����s���Ă���B
            uploadFile(mImagePath);
        }else {
            //�摜���Ȃ��Ƃ���comment�����o�^
            postMessages(null);
        }
    }
    //���e�����B�摜��Upload�����܂��������Ƃ��́Aurl�Ɍ��J��URL���Z�b�g�����
    public void postMessages(String url) {
        //�o�P�b�g����ݒ�B�o�P�b�g��DB�̃e�[�u���݂����Ȃ��́BExcel�̃V�[�g�݂����Ȃ��́B
        KiiBucket bucket = Kii.bucket("messages");
        KiiObject object = bucket.object();
        //Json�`����Key��comment���Z�b�g.{"comment":"���߂�Ƃł�","imageUrl":"http://xxx.com/xxxx"}
        object.set("comment", comment);
        //�摜������Ƃ������Z�b�g
        if(url != null) {
            object.set("imageUrl", url);
        }
        //�f�[�^��KiiCloud�ɕۑ�
        object.save(new KiiObjectCallBack() {
            //�ۑ����ʂ��A���Ă���R�[���o�b�N�֐��B�����I�ɌĂяo�����B
            @Override
            public void onSaveCompleted(int token, KiiObject object, Exception exception) {
                //�G���[���Ȃ��Ƃ�
                if (exception == null) {
                    // Intent �̃C���X�^���X���擾����BgetApplicationContext()�Ŏ����̃R���e�L�X�g���擾�B�J�ڐ�̃A�N�e�B�r�e�B�[��.class�Ŏw��
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    //Activity���I�����܂��B
                    finish();
                } else {
                    //e��KiiCloud���L�̃N���X���p�����Ă��鎞
                    if (exception instanceof CloudExecutionException)
                        //KiiCloud���L�̃G���[���b�Z�[�W��\���B�t�H�[�}�b�g���Ⴄ
                        showAlert(Util.generateAlertMessage((CloudExecutionException) exception));
                    else
                        //��ʓI�ȃG���[��\��
                        showAlert(exception.getLocalizedMessage());
                }
            }
        });
    }
    //�摜��KiiCloud��images��UP����B�Q�l�F�`���[�g���A���Ahttp://www.riaxdnp.jp/?p=6775
    private void uploadFile(String path) {
        //�C���[�W��ۑ�����o�P�b�g����ݒ�B���ׂĂ����ɕۑ�����message�ɂ͂���http�p�X��ݒ肷��B�o�P�b�g��DB�̃e�[�u���݂����Ȃ��́BExcel�̃V�[�g�݂����Ȃ��́B
        KiiBucket bucket = Kii.bucket("images");
        KiiObject object = bucket.object();
        //Up��Ɍ��J�ݒ肷��̂ŕۑ�
        mKiiImageObject = object;
        File f = new File(path);
        //KiiCloud��UP����C���X�^���X
        KiiUploader uploader = object.uploader(this, f);
        //�񓯊���Up����B
        uploader.transferAsync(new KiiRTransferCallback() {
            //����������
            @Override
            public void onTransferCompleted(KiiRTransfer operator, Exception e) {
                if (e == null) {
                    //�����̎�
                    //�摜���ꗗ�ŕ\�����邽�߁A���J��Ԃɂ���B�Q�l�Fhttp://www.riaxdnp.jp/?p=6841
                    // URI�w��Obj�����t���b�V�����āA�ŐV��Ԃɂ���
                    mKiiImageObject.refresh(new KiiObjectCallBack() {
                        public void onRefreshCompleted(int token, KiiObject object, Exception e) {
                            if (e == null) {
                                // ObjectBody�̌��J�ݒ肷��
                                object.publishBody(new KiiObjectPublishCallback() {
                                    @Override
                                    public void onPublishCompleted(String url, KiiObject kiiObject, Exception e) {
                                        Log.d("mogiurl", url);
                                        //�摜��URL�t����messages�ɓ��e����B
                                        postMessages(url);
                                    }
                                });
                            }
                        }
                    });


                } else {
                    //���s�̎�
                    Throwable cause = e.getCause();
                    if (cause instanceof CloudExecutionException)
                        showAlert(Util
                                .generateAlertMessage((CloudExecutionException) cause));
                    else
                        showAlert(e.getLocalizedMessage());
                }
            }
        });
    }
    //�G���[�_�C�A���O��\������
    void showAlert(String message) {
        DialogFragment newFragment = AlertDialogFragment.newInstance(R.string.operation_failed, message, null);
        newFragment.show(getFragmentManager(), "dialog");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_post, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}