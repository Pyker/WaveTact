package com.techcavern.wavetact.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.conn.util.InetAddressUtils;
import org.pircbotx.Colors;
import org.pircbotx.PircBotX;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class GeneralUtils {
    public static String buildMessage(int startint, int finishint, String[] args) {
        StringBuilder builder = new StringBuilder();
        for (int i = startint; i < finishint; i++) {
            builder.append(args[i]);
            builder.append(' ');
        }
        return builder.toString().trim();
    }

    public static String prism(String toprism) {
        String prism = "";
        int i = 0;
        int j = RandomUtils.nextInt(1, 7);
        for (char c : toprism.replace("\n", " ").toCharArray()) {
            if (i % 2 == 0 && i != 0) {
                j++;
                if (j > 6) {
                    j = j % 6;
                    if (j == 0) {
                        j = 1;
                    }
                }
            }
            System.out.println(j);
            prism += prism(c, j);
            i++;
        }
        return prism;
    }

    public static JsonObject getJsonObject(String url) throws Exception {
        String result = parseUrl(url);
        return new JsonParser().parse(result).getAsJsonObject();
    }

    public static JsonElement getJsonElement(String url) throws Exception {
        String result = parseUrl(url);
        return new JsonParser().parse(result);
    }

    public static String getJsonString(JsonArray array, String name) {
        String returning = "";
        for (int i = 0; i < array.size(); i++) {
            if (i == 0) {
                returning = array.get(i).getAsJsonObject().get(name).getAsString();
            } else {
                returning += ", " + array.get(i).getAsJsonObject().get(name).getAsString();
            }
        }
        return returning;
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static String parseUrl(String Url) throws Exception {
        URL url1 = new URL(Url);
        URLConnection conn = url1.openConnection();
        conn.addRequestProperty("user-agent", Registry.userAgent);
        String line;
        String result = "";
        BufferedReader buffereader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        while ((line = buffereader.readLine()) != null) {
            result += line.replaceAll("\n", " ") + "\n";
        }
        buffereader.close();
        return result;

    }

    public static JsonArray getJsonArray(String url) throws Exception {
        String result = parseUrl(url);
        return new JsonParser().parse(result).getAsJsonArray();
    }

    public static String getIP(String input, PircBotX Bot, boolean IPv6Priority) {
        String IP = "";
        if (input.contains(".") || input.contains(":")) {
            IP = input;
        } else {
            IP = IRCUtils.getHost(Bot, input);
        }
        if (InetAddressUtils.isIPv4Address(IP) || InetAddressUtils.isIPv6Address(IP)) {
            return IP;
        } else {
            IP = IP.replaceAll("http://|https://", "");
            try {
                InetAddress[] addarray = InetAddress.getAllByName(IP);
                String add = "";
                if (IPv6Priority) {
                    for (InetAddress add6 : addarray) {
                        if (InetAddressUtils.isIPv6Address(add6.getHostAddress()))
                            add = add6.getHostAddress();
                    }
                } else {
                    for (InetAddress add4 : addarray) {
                        if (InetAddressUtils.isIPv4Address(add4.getHostAddress()))
                            add = add4.getHostAddress();
                    }
                }
                if (add == null || add.isEmpty()) {
                    add = InetAddress.getByName(IP).getHostAddress();
                }
                return add;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static long getMilliSeconds(String input) {
        if (input.toLowerCase().endsWith("h")) {
            return Long.parseLong(input.replace("h", "")) * 60 * 60 * 1000;
        } else if (input.toLowerCase().endsWith("m")) {
            return Long.parseLong(input.replace("m", "")) * 60 * 1000;

        } else if (input.toLowerCase().endsWith("d")) {
            return Long.parseLong(input.replace("d", "")) * 24 * 60 * 60 * 1000;

        } else if (input.toLowerCase().endsWith("w")) {
            return Long.parseLong(input.replace("w", "")) * 7 * 24 * 60 * 60 * 1000;

        } else if (input.toLowerCase().endsWith("s")) {
            return Long.parseLong(input.replace("s", "")) * 1000;
        } else {
            return Long.parseLong(input);
        }
    }

    public static String[] removeColorsAndFormatting(String[] input) {
        for (int i = 0; i < input.length; i++) {
            input[i] = Colors.removeFormattingAndColors(input[i]);
        }
        return input;
    }

    public static String[] toArray(String input) {
        return StringUtils.split(input, " ");
    }

    public static String prism(char c, int number) {
        String result = null;
        System.out.println(number);
        switch (number) {
            case 1:
                result = Colors.RED + c;
                break;
            case 2:
                result = Colors.YELLOW + c;
                break;
            case 3:
                result = Colors.DARK_GREEN + c;
                break;
            case 4:
                result = Colors.CYAN + c;
                break;
            case 5:
                result = Colors.BLUE + c;
                break;
            case 6:
                result = Colors.MAGENTA + c;
                break;
        }
        return result;
    }

    public static String replaceVowelsWithAccents(String original) {
        if (original.contains("a"))
            original = original.replaceFirst("a", "á");
        else if (original.contains("e"))
            original = original.replaceFirst("e", "é");
        else if (original.contains("i"))
            original = original.replaceFirst("i", "í");
        else if (original.contains("o"))
            original = original.replaceFirst("o", "ó");
        else if (original.contains("u"))

            original = original.replaceFirst("u", "ú");
        else if (original.contains("y"))

            original = original.replaceFirst("y", "ý");
        else if (original.contains("A"))

            original = original.replaceFirst("A", "Á");
        else if (original.contains("E"))

            original = original.replaceFirst("E", "É");
        else if (original.contains("I"))

            original = original.replaceFirst("I", "Í");
        else if (original.contains("O"))

            original = original.replaceFirst("O", "Ó");
        else if (original.contains("U"))

            original = original.replaceFirst("U", "Ú");
        else if (original.contains("Y"))

            original = original.replaceFirst("Y", "Ý");
        return original;
    }

    public static int readInputStream(InputStream in)
            throws Exception {
        int i = 0;
        int j = 0;
        while (true) {
            int k = in.read();
            i |= (k & 0x7F) << j++ * 7;
            if (j > 5) {
                throw new RuntimeException("VarInt too big");
            }
            if ((k & 0x80) != 128) {
                break;
            }
        }
        return i;
    }

    public static void writeOutputStream(OutputStream out, int i)
            throws Exception {
        while (true) {
            if ((i & 0xFFFFFF80) == 0) {
                out.write(i);
                return;
            }

            out.write(i & 0x7F | 0x80);
            i >>>= 7;
        }
    }

    public static String getTimeFromSeconds(int seconds) {
        if (seconds > 60) {
            int minutes = seconds / 60;
            seconds = seconds % 60;
            if (minutes > 60) {
                int hours = minutes / 60;
                minutes = minutes % 60;
                if (hours > 24) {
                    int days = hours / 24;
                    hours = hours % 24;
                    return days + " days, " + hours + " hours, " + minutes + " minutes, " + seconds + " seconds";
                } else {
                    return hours + " hours, " + minutes + " minutes, " + seconds + " seconds";
                }
            } else {
                return minutes + " minutes, " + seconds + " seconds";
            }
        } else {
            return seconds + " seconds";
        }
    }

    public static String getDateFromSeconds(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        cal.setTimeInMillis(time * 1000);
        Locale locale = Locale.ENGLISH;
        return cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, locale) + ", " + cal.getDisplayName(Calendar.MONTH, Calendar.LONG, locale) + " " + (cal.get(Calendar.DAY_OF_MONTH)) + ", " + cal.get(Calendar.YEAR) + " at " + cal.get(Calendar.HOUR_OF_DAY) + ":" + formatTime(cal.get(Calendar.MINUTE)) + ":" + formatTime(cal.get(Calendar.SECOND)) + " (UTC)";
    }

    public static String formatTime(int time) {
        if (time < 10) {
            return 0 + String.valueOf(time);
        } else {
            return String.valueOf(time);
        }
    }
}


