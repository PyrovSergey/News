package udacity.com.news;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ResultSearchActivity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<List<News>> {
    // Адаптет для книг
    private NewsAdapter newsAdapter;

    /**
     * TextView that is displayed when the list is empty
     */
    private TextView mEmptyStateTextView;

    private static final String str1 = "http://content.guardianapis.com/search?order-by=newest&page-size=200&q=";
    private static final String str2 = "&api-key=test";
    private static String result;

    private static final int NEWS_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_search);

        result = str1 + MainActivity.requestString + str2;
        result = result.replaceAll(" ", "+");



        ListView newsListView = (ListView) findViewById(R.id.list);
        newsAdapter = new NewsAdapter(this, new ArrayList<News>());

        // Цепляем ссылку на "пустой" View
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        newsListView.setEmptyView(mEmptyStateTextView);

        newsListView.setAdapter(newsAdapter);

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Находим текущуе новость, на которю было нажатие
                News currentNews = newsAdapter.getItem(i);

                // Преобразование String URL в объект URI (для перехода в конструктор Intent)
                Uri newsUri = Uri.parse(currentNews.getUrl());

                // Создаем новое намерение для просмотра URI новости
                Intent newsIntent = new Intent(Intent.ACTION_VIEW, newsUri);

                // Посылаем команду на запуск браузера
                startActivity(newsIntent);
            }
        });

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(NEWS_LOADER_ID, null, this);

        Log.e("MyTAGS", "сработал метод initLoader()");

        // Получить ссылку на ConnectivityManager для проверки состояния сетевого подключения
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Получить информацию о текущей активной сети передачи данных по умолчанию
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // Если есть сетевое подключение, выберите данные
        if (networkInfo != null && networkInfo.isConnected()) {
            // Получите ссылку на LoaderManager, чтобы взаимодействовать с загрузчиками.
            loaderManager = getLoaderManager();

            // Инициализация загрузчика. Перейдите в константу ID ID, указанную выше, и передайте значение null для
            // связки. Перейдите в эту операцию для параметра LoaderCallbacks (который действителен
            // потому что эта активность реализует интерфейс LoaderCallbacks).
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            // В противном случае, ошибка отображения
            // Сначала скройте индикатор загрузки, чтобы было видно сообщение об ошибке
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Обновить пустое состояние без сообщения об ошибке подключения
            mEmptyStateTextView.setText("No internet connection");
        }
    }


    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        Log.e("MyTAGS", "сработал метод onCreateLoader()" );
        return new NewsLoader(this, result);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {

        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Очистите адаптер предыдущих данных новостей
        newsAdapter.clear();
        // Если есть допустимый список {@link News} s, добавьте их в адаптер
        // набор данных. Это приведет к обновлению ListView.
        if (news != null && !news.isEmpty()) {
            newsAdapter.addAll(news);
        }
        Log.e("MyTAGS", "сработал метод onLoadFinished()");
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        // Очистите адаптер предыдущих данных по новостям
        newsAdapter.clear();
        result = "";
        Log.e("MyTAGS", "сработал метод onLoaderReset()");
    }
}

