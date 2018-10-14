import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;


/**
 * Created by L on 18/10/13.
 */
public class Req {
    public static void main(String[] args) throws IOException {
        String sk_20181013 = SongsKey.getSk_20181013();
        String[] sks= sk_20181013.split("\\|");

        for(String sk:sks) {
            getSonginfo(sk);
        }

    }

    public static void getSonginfo(String sids) throws IOException{
        CloseableHttpClient cHttpClient_http= HttpClients.createDefault();
        String url="https://douban.fm/j/v2/redheart/songs";
        Map<String, String> paramdates=new HashMap<String, String>();
        paramdates.put("sids",sids);
        paramdates.put("kbps","192");
        paramdates.put("ck","HNML");
        SongInfo songInfo = new SongInfo();
        CloseableHttpResponse cr = exeRequest(cHttpClient_http, url, paramdates, null);
        String string = EntityUtils.toString(cr.getEntity());
        System.out.println(string);

//        File file=new File("/Users/L/develop/doubanDownload/song192.js");
//        BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
//        bufferedWriter.write(string);
//        bufferedWriter.close();

        //简单粗暴的用字符串来处理了，其实是json数据
        int t1 = string.indexOf("\"title\":")+9;
        int t2 = string.indexOf("\",\"subtype\":");
        songInfo.setTitle(string.substring(t1,t2).replace("/",""));
        int u1 = string.indexOf("\"name_usual\":")+14;
        int u2 = string.indexOf("\",\"related_site_id\":");
        songInfo.setName_usual(string.substring(u1, u2).replace("/",""));
        int url1 = string.indexOf("\"url\":")+7;
        int url2 = string.indexOf("\",\"length\"");
        songInfo.setUrl(string.substring(url1, url2).replace("\\",""));
        System.out.println(songInfo);


        CloseableHttpResponse cr2 = exeRequest(cHttpClient_http, songInfo.getUrl(), null, null);
        File songFile=new File("/Users/L/develop/doubanDownload/songForJava/"+songInfo.getTitle()+"-"+songInfo.getName_usual()+".mp3");
        FileOutputStream fos=new FileOutputStream(songFile);
        fos.write(EntityUtils.toByteArray(cr2.getEntity()));
        fos.close();


    }

    public static CloseableHttpResponse exeRequest(CloseableHttpClient cHttpClient, String url, Map<String, String> paramDatas, String jsession)
            throws ClientProtocolException, IOException{
        HttpPost httpPost=new HttpPost(url);
        // 设置请求参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if(paramDatas!=null){
            Set<Entry<String,String>> entrySet = paramDatas.entrySet();
            for(Iterator<Entry<String, String>> iterator = entrySet.iterator();iterator.hasNext();){
                Entry<String, String> entry = iterator.next();
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
        }
        HttpEntity reqEntity = new UrlEncodedFormEntity(nvps, Consts.UTF_8);
        httpPost.setEntity(reqEntity);
        httpPost.setHeader("Cookie", "_ga=GA1.2.1035160779.1485360884; bid=dIhilIOyvJM; flag=\"ok\"; dbcl2=\"47845117:aipkZ7wafBI\"; ck=HNML; _gid=GA1.2.1244555442.1539355049");

        CloseableHttpResponse closeableHttpResponse = cHttpClient.execute(httpPost);
//		closeableHttpResponse.close();
//		cHttpClient.close();
        return closeableHttpResponse;
    }
}
