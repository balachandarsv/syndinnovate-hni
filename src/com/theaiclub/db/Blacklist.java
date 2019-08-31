package com.theaiclub.db;

import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

public class Blacklist {
	public static final String TABLENAME = "blacklist";
	public static final String ID = "id";
	public static final String USER = "username";
	public static final String DATETIME = "datetime";
	public static final String BRANCH = "branch";
	public static final String LOCATION = "location";
	public static final String EMBEDDING = "emb1,emb2,emb3,emb4,emb5,emb6,emb7,emb8,emb9,emb10,emb11,emb12,emb13,emb14,emb15,emb16,emb17,emb18,emb19,emb20,emb21,emb22,emb23,emb24,emb25,emb26,emb27,emb28,emb29,emb30,emb31,emb32,emb33,emb34,emb35,emb36,emb37,emb38,emb39,emb40,emb41,emb42,emb43,emb44,emb45,emb46,emb47,emb48,emb49,emb50,emb51,emb52,emb53,emb54,emb55,emb56,emb57,emb58,emb59,emb60,emb61,emb62,emb63,emb64,emb65,emb66,emb67,emb68,emb69,emb70,emb71,emb72,emb73,emb74,emb75,emb76,emb77,emb78,emb79,emb80,emb81,emb82,emb83,emb84,emb85,emb86,emb87,emb88,emb89,emb90,emb91,emb92,emb93,emb94,emb95,emb96,emb97,emb98,emb99,emb100,emb101,emb102,emb103,emb104,emb105,emb106,emb107,emb108,emb109,emb110,emb111,emb112,emb113,emb114,emb115,emb116,emb117,emb118,emb119,emb120,emb121,emb122,emb123,emb124,emb125,emb126,emb127,emb128,emb129,emb130,emb131,emb132,emb133,emb134,emb135,emb136,emb137,emb138,emb139,emb140,emb141,emb142,emb143,emb144,emb145,emb146,emb147,emb148,emb149,emb150,emb151,emb152,emb153,emb154,emb155,emb156,emb157,emb158,emb159,emb160,emb161,emb162,emb163,emb164,emb165,emb166,emb167,emb168,emb169,emb170,emb171,emb172,emb173,emb174,emb175,emb176,emb177,emb178,emb179,emb180,emb181,emb182,emb183,emb184,emb185,emb186,emb187,emb188,emb189,emb190,emb191,emb192,emb193,emb194,emb195,emb196,emb197,emb198,emb199,emb200,emb201,emb202,emb203,emb204,emb205,emb206,emb207,emb208,emb209,emb210,emb211,emb212,emb213,emb214,emb215,emb216,emb217,emb218,emb219,emb220,emb221,emb222,emb223,emb224,emb225,emb226,emb227,emb228,emb229,emb230,emb231,emb232,emb233,emb234,emb235,emb236,emb237,emb238,emb239,emb240,emb241,emb242,emb243,emb244,emb245,emb246,emb247,emb248,emb249,emb250,emb251,emb252,emb253,emb254,emb255,emb256,emb257,emb258,emb259,emb260,emb261,emb262,emb263,emb264,emb265,emb266,emb267,emb268,emb269,emb270,emb271,emb272,emb273,emb274,emb275,emb276,emb277,emb278,emb279,emb280,emb281,emb282,emb283,emb284,emb285,emb286,emb287,emb288,emb289,emb290,emb291,emb292,emb293,emb294,emb295,emb296,emb297,emb298,emb299,emb300,emb301,emb302,emb303,emb304,emb305,emb306,emb307,emb308,emb309,emb310,emb311,emb312,emb313,emb314,emb315,emb316,emb317,emb318,emb319,emb320,emb321,emb322,emb323,emb324,emb325,emb326,emb327,emb328,emb329,emb330,emb331,emb332,emb333,emb334,emb335,emb336,emb337,emb338,emb339,emb340,emb341,emb342,emb343,emb344,emb345,emb346,emb347,emb348,emb349,emb350,emb351,emb352,emb353,emb354,emb355,emb356,emb357,emb358,emb359,emb360,emb361,emb362,emb363,emb364,emb365,emb366,emb367,emb368,emb369,emb370,emb371,emb372,emb373,emb374,emb375,emb376,emb377,emb378,emb379,emb380,emb381,emb382,emb383,emb384,emb385,emb386,emb387,emb388,emb389,emb390,emb391,emb392,emb393,emb394,emb395,emb396,emb397,emb398,emb399,emb400,emb401,emb402,emb403,emb404,emb405,emb406,emb407,emb408,emb409,emb410,emb411,emb412,emb413,emb414,emb415,emb416,emb417,emb418,emb419,emb420,emb421,emb422,emb423,emb424,emb425,emb426,emb427,emb428,emb429,emb430,emb431,emb432,emb433,emb434,emb435,emb436,emb437,emb438,emb439,emb440,emb441,emb442,emb443,emb444,emb445,emb446,emb447,emb448,emb449,emb450,emb451,emb452,emb453,emb454,emb455,emb456,emb457,emb458,emb459,emb460,emb461,emb462,emb463,emb464,emb465,emb466,emb467,emb468,emb469,emb470,emb471,emb472,emb473,emb474,emb475,emb476,emb477,emb478,emb479,emb480,emb481,emb482,emb483,emb484,emb485,emb486,emb487,emb488,emb489,emb490,emb491,emb492,emb493,emb494,emb495,emb496,emb497,emb498,emb499,emb500,emb501,emb502,emb503,emb504,emb505,emb506,emb507,emb508,emb509,emb510,emb511,emb512";

	public static JSONArray getBlacklist() {
		try {
			JSONObject result = DBUtils
					.select(TABLENAME,
							USER + "," + DATETIME + "," + BRANCH + ","
									+ LOCATION + "," + EMBEDDING,
							null, null, null);
			if (result.has("result")) {
				return result.getJSONArray("result");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean insert(String[] values) {
		try {
			return DBUtils.insert(TABLENAME, USER + "," + DATETIME + ","
					+ BRANCH + "," + LOCATION + "," + EMBEDDING, values);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
}
