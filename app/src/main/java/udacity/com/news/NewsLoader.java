package udacity.com.news;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

// Класс загрузчик новостей NewsLoader для выполнения в асинхронном потоке
public class NewsLoader extends AsyncTaskLoader<List<News>> {

    String url;

    public NewsLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
        Log.e("MyTAGS", "сработал метод onStartLoading()");
    }

    @Override
    public List<News> loadInBackground() {
        // Если длина переданного массива urls меньше 1 или первая строчка равна null
        // то возвращаем null и ничего дальше не делаем
        if (url == null) {
            return null;
        }

        // иначе создаем список землетрясений result и возвращаем его
        List<News> news = QueryUtils.fetchNewsData(url);
        Log.e("MyTAGS", "сработал метод loadInBackground()");
        Log.e("MyTAGS", url.toString());
        return news;
    }
}
