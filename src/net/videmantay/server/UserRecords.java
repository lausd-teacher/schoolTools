package net.videmantay.server;

import com.google.common.collect.ImmutableMap;

public class UserRecords {
	
 public static final ImmutableMap<String, UserInfo> users = 
		new ImmutableMap.Builder<String, UserInfo>().
				put("melanie@videmantay.net", new UserInfo("Melanie", "Acuna", "http://commondatastorage.googleapis.com/kim_school%2Fkids%2FmelanieWeb.jpg", "B")).//1
				put("angel@videmantay.net", new UserInfo("Angel", "Alvarado", "http://commondatastorage.googleapis.com/kim_school%2Fkids%2FangelWeb.jpg")).//2
				put("darwin@videmantay.net", new UserInfo("Darwin", "Argueto", "http://commondatastorage.googleapis.com/kim_school%2Fkids%2FdarwinWeb.jpg")).//3
				put("dayana@videmantay.net", new UserInfo("Dayana", "Calvillo", "http://commondatastorage.googleapis.com/kim_school%2Fkids%2FdayanaWeb.jpg")).//4
				put("amy@videmantay.net", new UserInfo("Amy", "Cardenas", "http://commondatastorage.googleapis.com/kim_school%2Fkids%2FamyWeb.jpg")).//5
				put("andrew.c@videmantay.net", new UserInfo("Andrew", "Chacon", "http://commondatastorage.googleapis.com/kim_school%2Fkids%2FandrewWeb.JPG")).//6
				put("michelle@videmantay.net", new UserInfo("Michelle", "Chavez", "http://commondatastorage.googleapis.com/kim_school%2Fkids%2FmichelleWeb.JPG")).//7
				put("jennifer@videmantay.net", new UserInfo("Jennifer", "Lopez", "img/jlo.jpg", "I")).//8
				put("alexander@videmantay.net", new UserInfo("Alexander", "Cruz", "http://commondatastorage.googleapis.com/kim_school%2Fkids%2FalexWeb.jpg")).//9
				put("jordi@videmantay.net", new UserInfo("Jordi", "Cruz", "http://commondatastorage.googleapis.com/kim_school%2Fkids%2FjordiWeb.JPG")).//10
				put("andrew.d@videmantay.net", new UserInfo("Drew", "De/La/Pe&ntilde;a", "http://commondatastorage.googleapis.com/kim_school%2Fkids%2FdrewWeb.jpg")).//11
				put("jade@videmantay.net", new UserInfo("Jade", "Espinoza", "http://commondatastorage.googleapis.com/kim_school%2Fkids%2FjadeWeb.jpg")).//12
				put("brenda@videmantay.net", new UserInfo("Brenda", "Garcia", "http://commondatastorage.googleapis.com/kim_school%2Fkids%2Fbrenda.jpg")).//13
				put("alexa@videmantay.net", new UserInfo("Alexa", "Lopez", "http://commondatastorage.googleapis.com/kim_school%2Fkids%2FalexaWeb.jpg")).//15
				put("axel@videmantay.net", new UserInfo("Axel", "Lopez", "http://commondatastorage.googleapis.com/kim_school%2Fkids%2FaxelWeb.JPG")).//16
				put("sydney@videmantay.net", new UserInfo("Sydney", "Luna", "http://commondatastorage.googleapis.com/kim_school%2Fkids%2FsydneyWeb.jpg")).//17
				put("kimberly@videmantay.net", new UserInfo("Kimberly", "Martinez", "http://commondatastorage.googleapis.com/kim_school%2Fkids%2FkimberlyWeb.jpg")).//18
				put("fernanda@videmantay.net", new UserInfo("Fernanda", "Miguel", "http://commondatastorage.googleapis.com/kim_school%2Fkids%2FfernandaWeb.jpg")).//19
				put("natalie@videmantay.net", new UserInfo("Natalie", "Perez", "http://commondatastorage.googleapis.com/kim_school%2Fkids%2FnatalieWeb.jpg")).//20
				put("kayla@videmantay.net", new UserInfo("Kayla", "Ponce", "http://commondatastorage.googleapis.com/kim_school%2Fkids%2FkaylaWeb.jpg")).//21
				put("nathan@videmantay.net", new UserInfo("Nathan", "Reynaga", "http://commondatastorage.googleapis.com/kim_school%2Fkids%2FnathanWeb.jpg")).//22
				put("ayelen@videmantay.net", new UserInfo("Ayelen", "Robles", "http://commondatastorage.googleapis.com/kim_school%2Fkids%2FayelenWeb.jpg")).//23
				put("valerie@videmantay.net", new UserInfo("Valerie", "Rosales", "http://commondatastorage.googleapis.com/kim_school%2Fkids%2FvalerieWeb.JPG")).//24
				put("ainsley@videmantay.net", new UserInfo("Ainsley", "Ruiz", "http://commondatastorage.googleapis.com/kim_school%2Fkids%2Fainsley.jpg")).//25
				put("jhonny@videmantay.net", new UserInfo("Jhonny", "Villagrana", "http://commondatastorage.googleapis.com/kim_school%2Fkids%2FjhonnyWeb.jpg")).//
				build();
 




private UserRecords(){
	
}

static public  UserInfo getUser(String key){
	return users.get(key);
}

}
