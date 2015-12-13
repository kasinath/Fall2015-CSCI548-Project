package facebook.user;


import java.util.ArrayList;
import java.util.List;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Like;
import facebook4j.RawAPIResponse;
import facebook4j.ResponseList;
import facebook4j.User;
import facebook4j.internal.org.json.JSONException;
import facebook4j.json.DataObjectFactory;

public class RetrieveUserInformation {
	public  List<String> retieveUserInfo() throws FacebookException, JSONException {
		Facebook facebook = new FacebookFactory().getInstance();
		ResponseList<Like> results = facebook.getUserLikes();
		List<String> ret = new ArrayList<>();
		System.out.println(results.size());
		for (Like like : results) {
			System.out.println(like.getName() +" : " +like.getCategory());
			
			String[] split = like.getCategory().split("/");

			for(String item : split)
			{
				item = item.replaceAll(" ", "_");
				if(!ret.contains(item))
					ret.add(item);
			}

		}
		
		return ret;
		
		/*RawAPIResponse res = null;
		try {
			res = facebook.callGetAPI("me");
		} catch (FacebookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		User me = DataObjectFactory.createUser(res.asString());*/

	}
	
	
	public static void main(String[] args) throws FacebookException, JSONException {
		RetrieveUserInformation r = new RetrieveUserInformation();
		r.retieveUserInfo();
	}
}
