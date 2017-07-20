package mituo.wshoto.com.mituo;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

/**
 * Created by user on 2017/7/15.
 */

public class NullStringToEmptyAdapterFactory implements TypeAdapterFactory {
    public TypeAdapter create(Gson gson, TypeToken type) {
        Class rawType = (Class) type.getRawType();        if (rawType != String.class) {
            return null;        }
        return (TypeAdapter) new StringNullAdapter();    }
}
