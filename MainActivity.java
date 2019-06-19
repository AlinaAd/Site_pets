package com.example.openvc_test4;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.core.MatOfRect;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.R;


public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
  //  private ImageView imageView2;
    private final int Pick_image = 1;
  //  private string path="path.xml";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
        //------------------------------------------------------------------
        imageView = (ImageView)findViewById(R.id.imageView);
        Button Galery = (Button) findViewById(R.id.button);
        Galery.setOnClickListener (new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                //Вызываем стандартную галерею для выбора изображения с помощью Intent.ACTION_PICK:
 		        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
 		        //Тип получаемых объектов - image:
 		        photoPickerIntent.setType("image/*");
 		        //Запускаем переход с ожиданием обратного результата в виде информации об изображении:
 		        startActivityForResult(photoPickerIntent, Pick_image);

            }

        });

        Button Haara = (Button) findViewById(R.id.button3);
        imageView2 = (ImageView)findViewById(R.id.imageView2);
        Haara.setOnClickListener (new View.OnClickListener()
        {
            @Overridepublic void onClick (View view)
            {
                Mat img = Imgcodecs.imread("C:\\book\\opencv\\luba1.jpg");
                if (img.empty()) {
                    System.out.println("Не удалось загрузить изображение");
                    return;
                }
                CascadeClassifier object_detector = new CascadeClassifier();
                String path = "path.xml";
                if (!object_detector.load(path)) {
                    System.out.println("Не удалось загрузить классификатор " + path);
                    return;
                }
                MatOfRect mashrooms = new MatOfRect();
                mashrooms.detectMultiScale(img, mashrooms);
                for (Rect r : mashrooms.toList()) {
                    Imgproc.rectangle(img, new Point(r.x, r.y),
                            new Point(r.x + r.width, r.y + r.height),
                            CvUtils.COLOR_WHITE, 2);
                }
                CvUtilsFX.showImage(img, "Результат");
                img.release();
            }
        });

    }
    //Обрабатываем результат выбора в галерее:
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case Pick_image:
                if(resultCode == RESULT_OK){
                    try {

                        //Получаем URI изображения, преобразуем его в Bitmap
                        //объект и отображаем в элементе ImageView нашего интерфейса:
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        imageView.setImageBitmap(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
        }}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
