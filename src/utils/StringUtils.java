package utils;

public class StringUtils {
    public static String removeAccent(String input){
        return input.replaceAll("á|à|â|ã|ä","a")
                .replaceAll("é|è|ê|ë","e")
                .replaceAll("í|ì|î|ï","i")
                .replaceAll("ó|ò|ô|ö","o")
                .replaceAll("ú|ù|û|ü","u");
    }
}
