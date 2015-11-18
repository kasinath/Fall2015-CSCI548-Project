package facebook.user;

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
	public static void main(String[] args) throws FacebookException, JSONException {
		Facebook facebook = new FacebookFactory().getInstance();
		ResponseList<Like> results = facebook.getUserLikes();
		for (Like like : results) {
			System.out.println(like.getName() +" : " +like.getCategory());
		}
		RawAPIResponse res = null;
		try {
			res = facebook.callGetAPI("me");
		} catch (FacebookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		User me = DataObjectFactory.createUser(res.asString());

	}
}
