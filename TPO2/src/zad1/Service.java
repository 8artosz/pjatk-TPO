/**
 *
 *  @author Wasilewski Bartosz S20296
 *
 */

package zad1;


import java.io.IOException;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.OpenWeatherMap;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Service {

    String country = null;
    String text = "";
    Map<String,String> map = new HashMap<String,String>();{
        map.put("Andorra, Principality Of", "AD");
        map.put("United Arab Emirates", "AE");
        map.put("Afghanistan, Islamic State Of", "AF");
        map.put("Antigua And Barbuda", "AG");
        map.put("Anguilla", "AI");
        map.put("Albania", "AL");
        map.put("Armenia", "AM");
        map.put("Netherlands Antilles", "AN");
        map.put("Angola", "AO");
        map.put("Antarctica", "AQ");
        map.put("Argentina", "AR");
        map.put("American Samoa", "AS");
        map.put("Austria", "AT");
        map.put("Australia", "AU");
        map.put("Aruba", "AW");
        map.put("Azerbaidjan", "AZ");
        map.put("Bosnia-Herzegovina", "BA");
        map.put("Barbados", "BB");
        map.put("Bangladesh", "BD");
        map.put("Belgium", "BE");
        map.put("Burkina Faso", "BF");
        map.put("Bulgaria", "BG");
        map.put("Bahrain", "BH");
        map.put("Burundi", "BI");
        map.put("Benin", "BJ");
        map.put("Bermuda", "BM");
        map.put("Brunei Darussalam", "BN");
        map.put("Bolivia", "BO");
        map.put("Brazil", "BR");
        map.put("Bahamas", "BS");
        map.put("Bhutan", "BT");
        map.put("Bouvet Island", "BV");
        map.put("Botswana", "BW");
        map.put("Belarus", "BY");
        map.put("Belize", "BZ");
        map.put("Canada", "CA");
        map.put("Cocos (Keeling) Islands", "CC");
        map.put("Central African Republic", "CF");
        map.put("Congo, The Democratic Republic Of The", "CD");
        map.put("Congo", "CG");
        map.put("Switzerland", "CH");
        map.put("Ivory Coast (Cote D'Ivoire)", "CI");
        map.put("Cook Islands", "CK");
        map.put("Chile", "CL");
        map.put("Cameroon", "CM");
        map.put("China", "CN");
        map.put("Colombia", "CO");
        map.put("Costa Rica", "CR");
        map.put("Former Czechoslovakia", "CS");
        map.put("Cuba", "CU");
        map.put("Cape Verde", "CV");
        map.put("Christmas Island", "CX");
        map.put("Cyprus", "CY");
        map.put("Czech Republic", "CZ");
        map.put("Germany", "DE");
        map.put("Djibouti", "DJ");
        map.put("Denmark", "DK");
        map.put("Dominica", "DM");
        map.put("Dominican Republic", "DO");
        map.put("Algeria", "DZ");
        map.put("Ecuador", "EC");
        map.put("Estonia", "EE");
        map.put("Egypt", "EG");
        map.put("Western Sahara", "EH");
        map.put("Eritrea", "ER");
        map.put("Spain", "ES");
        map.put("Ethiopia", "ET");
        map.put("Finland", "FI");
        map.put("Fiji", "FJ");
        map.put("Falkland Islands", "FK");
        map.put("Micronesia", "FM");
        map.put("Faroe Islands", "FO");
        map.put("France", "FR");
        map.put("France (European Territory)", "FX");
        map.put("Gabon", "GA");
        map.put("Great Britain", "UK");
        map.put("Grenada", "GD");
        map.put("Georgia", "GE");
        map.put("French Guyana", "GF");
        map.put("Ghana", "GH");
        map.put("Gibraltar", "GI");
        map.put("Greenland", "GL");
        map.put("Gambia", "GM");
        map.put("Guinea", "GN");
        map.put("Guadeloupe (French)", "GP");
        map.put("Equatorial Guinea", "GQ");
        map.put("Greece", "GR");
        map.put("S. Georgia & S. Sandwich Isls.", "GS");
        map.put("Guatemala", "GT");
        map.put("Guam (USA)", "GU");
        map.put("Guinea Bissau", "GW");
        map.put("Guyana", "GY");
        map.put("Hong Kong", "HK");
        map.put("Heard And McDonald Islands", "HM");
        map.put("Honduras", "HN");
        map.put("Croatia", "HR");
        map.put("Haiti", "HT");
        map.put("Hungary", "HU");
        map.put("Indonesia", "ID");
        map.put("Ireland", "IE");
        map.put("Israel", "IL");
        map.put("India", "IN");
        map.put("British Indian Ocean Territory", "IO");
        map.put("Iraq", "IQ");
        map.put("Iran", "IR");
        map.put("Iceland", "IS");
        map.put("Italy", "IT");
        map.put("Jamaica", "JM");
        map.put("Jordan", "JO");
        map.put("Japan", "JP");
        map.put("Kenya", "KE");
        map.put("Kyrgyz Republic (Kyrgyzstan)", "KG");
        map.put("Cambodia, Kingdom Of", "KH");
        map.put("Kiribati", "KI");
        map.put("Comoros", "KM");
        map.put("Saint Kitts & Nevis Anguilla", "KN");
        map.put("North Korea", "KP");
        map.put("South Korea", "KR");
        map.put("Kuwait", "KW");
        map.put("Cayman Islands", "KY");
        map.put("Kazakhstan", "KZ");
        map.put("Laos", "LA");
        map.put("Lebanon", "LB");
        map.put("Saint Lucia", "LC");
        map.put("Liechtenstein", "LI");
        map.put("Sri Lanka", "LK");
        map.put("Liberia", "LR");
        map.put("Lesotho", "LS");
        map.put("Lithuania", "LT");
        map.put("Luxembourg", "LU");
        map.put("Latvia", "LV");
        map.put("Libya", "LY");
        map.put("Morocco", "MA");
        map.put("Monaco", "MC");
        map.put("Moldavia", "MD");
        map.put("Madagascar", "MG");
        map.put("Marshall Islands", "MH");
        map.put("Macedonia", "MK");
        map.put("Mali", "ML");
        map.put("Myanmar", "MM");
        map.put("Mongolia", "MN");
        map.put("Macau", "MO");
        map.put("Northern Mariana Islands", "MP");
        map.put("Martinique (French)", "MQ");
        map.put("Mauritania", "MR");
        map.put("Montserrat", "MS");
        map.put("Malta", "MT");
        map.put("Mauritius", "MU");
        map.put("Maldives", "MV");
        map.put("Malawi", "MW");
        map.put("Mexico", "MX");
        map.put("Malaysia", "MY");
        map.put("Mozambique", "MZ");
        map.put("Namibia", "NA");
        map.put("New Caledonia (French)", "NC");
        map.put("Niger", "NE");
        map.put("Norfolk Island", "NF");
        map.put("Nigeria", "NG");
        map.put("Nicaragua", "NI");
        map.put("Netherlands", "NL");
        map.put("Norway", "NO");
        map.put("Nepal", "NP");
        map.put("Nauru", "NR");
        map.put("Neutral Zone", "NT");
        map.put("Niue", "NU");
        map.put("New Zealand", "NZ");
        map.put("Oman", "OM");
        map.put("Panama", "PA");
        map.put("Peru", "PE");
        map.put("Polynesia (French)", "PF");
        map.put("Papua New Guinea", "PG");
        map.put("Philippines", "PH");
        map.put("Pakistan", "PK");
        map.put("Poland", "PL");
        map.put("Saint Pierre And Miquelon", "PM");
        map.put("Pitcairn Island", "PN");
        map.put("Puerto Rico", "PR");
        map.put("Portugal", "PT");
        map.put("Palau", "PW");
        map.put("Paraguay", "PY");
        map.put("Qatar", "QA");
        map.put("Reunion (French)", "RE");
        map.put("Romania", "RO");
        map.put("Russian Federation", "RU");
        map.put("Rwanda", "RW");
        map.put("Saudi Arabia", "SA");
        map.put("Solomon Islands", "SB");
        map.put("Seychelles", "SC");
        map.put("Sudan", "SD");
        map.put("Sweden", "SE");
        map.put("Singapore", "SG");
        map.put("Saint Helena", "SH");
        map.put("Slovenia", "SI");
        map.put("Svalbard And Jan Mayen Islands", "SJ");
        map.put("Slovak Republic", "SK");
        map.put("Sierra Leone", "SL");
        map.put("San Marino", "SM");
        map.put("Senegal", "SN");
        map.put("Somalia", "SO");
        map.put("Suriname", "SR");
        map.put("Saint Tome (Sao Tome) And Principe", "ST");
        map.put("Former USSR", "SU");
        map.put("El Salvador", "SV");
        map.put("Syria", "SY");
        map.put("Swaziland", "SZ");
        map.put("Turks And Caicos Islands", "TC");
        map.put("Chad", "TD");
        map.put("French Southern Territories", "TF");
        map.put("Togo", "TG");
        map.put("Thailand", "TH");
        map.put("Tadjikistan", "TJ");
        map.put("Tokelau", "TK");
        map.put("Turkmenistan", "TM");
        map.put("Tunisia", "TN");
        map.put("Tonga", "TO");
        map.put("East Timor", "TP");
        map.put("Turkey", "TR");
        map.put("Trinidad And Tobago", "TT");
        map.put("Tuvalu", "TV");
        map.put("Taiwan", "TW");
        map.put("Tanzania", "TZ");
        map.put("Ukraine", "UA");
        map.put("Uganda", "UG");
        map.put("United Kingdom", "UK");
        map.put("USA Minor Outlying Islands", "UM");
        map.put("United States", "US");
        map.put("Uruguay", "UY");
        map.put("Uzbekistan", "UZ");
        map.put("Holy See (Vatican City State)", "VA");
        map.put("Saint Vincent & Grenadines", "VC");
        map.put("Venezuela", "VE");
        map.put("Virgin Islands (British)", "VG");
        map.put("Virgin Islands (USA)", "VI");
        map.put("Vietnam", "VN");
        map.put("Vanuatu", "VU");
        map.put("Wallis And Futuna Islands", "WF");
        map.put("Samoa", "WS");
        map.put("Yemen", "YE");
        map.put("Mayotte", "YT");
        map.put("Yugoslavia", "YU");
        map.put("South Africa", "ZA");
        map.put("Zambia", "ZM");
        map.put("Zaire", "ZR");
        map.put("Zimbabwe", "ZW");

    }

    public Service(String country) {
        this.country = country;
    }

    public String getWeather(String city) {

        OpenWeatherMap owm = new OpenWeatherMap(OpenWeatherMap.Units.METRIC, "be17e12526fea34450bf333ea7cd04ca");
        String JSON = null;
        try {
            CurrentWeather currentWeather = owm.currentWeatherByCityName(city,map.get(country));

            if (currentWeather.hasBaseStation()) {
                JSON = currentWeather.getRawResponse();
            }
            text="";
            JSONObject jsonObj = new JSONObject(JSON);
                text += "Country: " + jsonObj.getJSONObject("sys").get("country") + "\n";
                text += "Loc: " + city + "\n";
                text += "Temperature: " + jsonObj.getJSONObject("main").get("temp") + "\n";
                text += "Feels like: " + jsonObj.getJSONObject("main").get("feels_like") + "\n";
                text += "Humidity: " + jsonObj.getJSONObject("main").get("humidity") + "\n";
                text += "Pressure: " + jsonObj.getJSONObject("main").get("pressure") + "\n";
        } catch (IOException | JSONException e) {

            e.printStackTrace();
        }

        return text;
    }

    Double getRateFor(String codeIndex) {

        String BASE_URL = "https://api.exchangerate.host";
        Locale locale = new Locale("",map.get(country));
        Locale.setDefault(Locale.ENGLISH);
        Currency currency = Currency.getInstance(locale);



        HttpGet get = new HttpGet(BASE_URL + "/latest" + "?base=" + currency.getCurrencyCode() + "&symbols=" + codeIndex);

        Double exchangeRate = null;

        try (CloseableHttpClient httpClient = HttpClients.createDefault(); CloseableHttpResponse response = httpClient.execute(get);) {

            HttpEntity entity = response.getEntity();
            JSONObject exchangeRates = new JSONObject(EntityUtils.toString(entity));

                exchangeRate = exchangeRates.getJSONObject("rates").getDouble(codeIndex);
        } catch (IOException | JSONException e) {

            e.printStackTrace();
        }

        return exchangeRate;
    }

    Double getNBPRate() {
        Double exchangeRate = null;
        Locale locale = new Locale("", map.get(country));
        Locale.setDefault(Locale.ENGLISH);
        Currency currency = Currency.getInstance(locale);
        String code = currency.getCurrencyCode();


        HttpGet get1 = new HttpGet("http://www.nbp.pl/kursy/kursya.html");
        HttpGet get2 = new HttpGet("http://www.nbp.pl/kursy/kursyb.html");


        try (CloseableHttpClient httpClient1 = HttpClients.createDefault(); CloseableHttpResponse response1 = httpClient1.execute(get1); CloseableHttpClient httpClient2 = HttpClients.createDefault(); CloseableHttpResponse response2 = httpClient2.execute(get2);) {

            HttpEntity entity1 = response1.getEntity();
            String exchangeRates1 = EntityUtils.toString(entity1);
            HttpEntity entity2 = response2.getEntity();
            String exchangeRates2 = EntityUtils.toString(entity2);
            int codeIndex_1 = exchangeRates1.indexOf(code);
            int codeIndex_2 = exchangeRates2.indexOf(code);
            if (codeIndex_1 != -1) {
                if (!country.equals("Poland")) {

                    int rateIndex = codeIndex_1 + 65;
                    exchangeRate = Double.valueOf(exchangeRates1.substring(rateIndex, rateIndex + 6).replace(',', '.'));

                } else exchangeRate = 1.0;
            }
            else{
                if (!country.equals("Poland")) {

                    int rateIndex = codeIndex_2 + 65;
                    exchangeRate = Double.valueOf(exchangeRates2.substring(rateIndex, rateIndex + 6).replace(',', '.'));

                } else exchangeRate = 1.0;
            }
        } catch (IOException e) {

            e.printStackTrace();
        }

        return exchangeRate;
    }

    public String getPage(String city) {


        if (city != null) {
            return "https://wikipedia.org/wiki/" + city;
        }

        return city;
    }
}