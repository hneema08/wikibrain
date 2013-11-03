package org.wikapidia.core.dao.live;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.wikapidia.core.lang.Language;
import org.wikapidia.core.model.LocalCategory;
import org.wikapidia.core.model.LocalCategoryMember;
import org.wikapidia.core.model.Title;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Toby "Jiajun" Li
 * Date: 11/3/13
 * Time: 1:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class LocalCategoryListQueryReply extends QueryReply {
    public List<LocalCategory> categoryList = new ArrayList<LocalCategory>();

    public LocalCategoryListQueryReply(String text, Language language){
        Gson gson = new Gson();
        JsonParser jp = new JsonParser();
        JsonObject test = jp.parse(text).getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> pageSet = jp.parse(text).getAsJsonObject().get("query").getAsJsonObject().get("pages").getAsJsonObject().entrySet();

        for (Map.Entry<String, JsonElement> entry: pageSet)
        {
            Long pageId = entry.getValue().getAsJsonObject().get("pageid").getAsLong();
            String title = entry.getValue().getAsJsonObject().get("title").getAsString();

            Long nameSpace = entry.getValue().getAsJsonObject().get("ns").getAsLong();
            for(JsonElement elem: entry.getValue().getAsJsonObject().get("categories").getAsJsonArray()){

                try{
                    Title categoryTitle = new Title( elem.getAsJsonObject().get("title").getAsString(), language);
                    categoryList.add(new LocalCategory(language, new LocalCategoryLiveDao().getIdByTitle(categoryTitle), categoryTitle));
                }
                catch(Exception e){

                }

            }
            this.pageId = pageId;
            this.title = title;
            this.pageLanguage = pageLanguage;
            this.nameSpace = nameSpace;

        }

    }

    /**
     *
     * @return a list of categories that an article belongs to
     */
    public List<LocalCategory> getCategoryList(){
        return categoryList;
    }


}



