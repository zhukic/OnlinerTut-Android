package rus.tutby.news;


import rus.tutby.MyApplication;
import rus.tutby.provider.Provider;

public class FeedBuildDate {
    private static String tutMainBuildDate = "";
    private static String tutLastBuildDate = "";
    private static String tutPoliticBuildDate = "";
    private static String tutEconomyBuildDate = "";
    private static String tutFinanceBuildDate = "";
    private static String tutSocietyBuildDate = "";
    private static String tutWorldBuildDate = "";
    private static String tutSportBuildDate = "";
    private static String tutITBuildDate = "";
    private static String tutAutoBuildDate = "";
    private static String onlinerMainBuildDate = "";
    private static String onlinerPeopleBuildDate = "";
    private static String onlinerAutoBuildDate = "";
    private static String onlinerTechnologyBuildDate = "";
    private static String onlinerRealtBuildDate = "";

    public static String getBuildDate(String category) {
        if (MyApplication.getProvider() == Provider.TUT) {
            switch (category) {
                case "Главное":
                    return tutMainBuildDate;
                case "Последние":
                    return tutLastBuildDate;
                case "Политика":
                    return tutPoliticBuildDate;
                case "Экономика":
                    return tutEconomyBuildDate;
                case "Финансы":
                    return tutFinanceBuildDate;
                case "Общество":
                    return tutSocietyBuildDate;
                case "В мире":
                    return tutWorldBuildDate;
                case "Спорт":
                    return tutSportBuildDate;
                case "42":
                    return tutITBuildDate;
                case "Авто":
                    return tutAutoBuildDate;
            }
        }
        if (MyApplication.getProvider() == Provider.ONLINER) {
            switch (category) {
                case "Главное":
                    return onlinerMainBuildDate;
                case "Люди":
                    return onlinerPeopleBuildDate;
                case "Авто":
                    return onlinerAutoBuildDate;
                case "Технологии":
                    return onlinerTechnologyBuildDate;
                case "Недвижимость":
                    return onlinerRealtBuildDate;
            }
        }
        return null;
    }
    
    public static void changeBuildDate(String category, String lastBuildDate) {
        if (MyApplication.getProvider() == Provider.TUT) {
            switch (category) {
                case "Главное":
                    tutMainBuildDate = lastBuildDate;
                case "Последние":
                    tutLastBuildDate = lastBuildDate;
                case "Политика":
                    tutPoliticBuildDate = lastBuildDate;
                case "Экономика":
                    tutEconomyBuildDate = lastBuildDate;
                case "Финансы":
                    tutFinanceBuildDate = lastBuildDate;
                case "Общество":
                    tutSocietyBuildDate = lastBuildDate;
                case "В мире":
                    tutWorldBuildDate = lastBuildDate;
                case "Спорт":
                    tutSportBuildDate = lastBuildDate;
                case "42":
                    tutITBuildDate = lastBuildDate;
                case "Авто":
                    tutAutoBuildDate = lastBuildDate;
            }
        }
        if (MyApplication.getProvider() == Provider.ONLINER) {
            switch (category) {
                case "Главное":
                    onlinerMainBuildDate = lastBuildDate;
                case "Люди":
                    onlinerPeopleBuildDate = lastBuildDate;
                case "Авто":
                    onlinerAutoBuildDate = lastBuildDate;
                case "Технологии":
                    onlinerTechnologyBuildDate = lastBuildDate;
                case "Недвижимость":
                    onlinerRealtBuildDate = lastBuildDate;
            }
        }
    }
}
