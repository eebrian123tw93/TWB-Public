package twb.conwaybrian.com.twbandroid;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import twb.conwaybrian.com.twbandroid.model.Article;
import twb.conwaybrian.com.twbandroid.shuoApi.ShuoApiService;

public class BottomNavigationActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);
        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new HomeFragment()).commit();


//        Observer<Response<JsonArray>>observer=new Observer<Response<JsonArray>>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(Response<JsonArray> response) {
//                System.out.println(response.code());
//                JsonArray jsonArray=response.body();
//                System.out.println(jsonArray);
//
//                Type listType = new TypeToken<List<Article>>() {}.getType();
//
//                List<Article> yourList = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create().fromJson(jsonArray, listType);
//                System.out.println(yourList.size());
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        };
//        ShuoApiService.getInstance().getArticles(observer,"qwe",1,1,false);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment;
        switch (menuItem.getItemId()){
            case R.id.profile:
                fragment=new ProfileFragment();
                break;
            case R.id.home:
                fragment=new HomeFragment();
                break;
            default:
                fragment=new HomeFragment();
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,fragment).commit();
        return true;
    }

}
