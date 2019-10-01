package ngram;

public class ProcessingText {
    public static String processingString(String str) {
        str = str.replace(",", " ");
        str = str.replace(".", " ");
        str = str.replace("-", " ");
        str = str.replace("!", " ");
        str = str.replace("?", " ");
        str = str.replace("—", " ");
        str = str.replace(";", " ");
        str = str.replace("(", " ");
        str = str.replace(")", " ");
        str = str.replace(":", " ");
        str = str.replace("–", " ");
        str = str.replace("\"", " ");
        str = str.replace("«", " ");
        str = str.replace("…", " ");
        str = str.replace("“", " ");
        str = str.replace("»", " ");
        str = str.replace(" ", " ");
        str = str.replace(">"," ");
        str = str.replace("’"," ");
        str =str.replace("―"," ");
        str=str.replace("́"," ");
        str = str.replace("["," ");
        str = str.replace("]"," ");
        str =str.replace("'"," ");
        str=str.replace("*"," ");
        str=str.replace("̀"," ");
        str=str.replace("<"," ");
        str=str.replace("№"," ");
        str=str.replace("/"," ");
        str=str.replace("\t"," ");

        str = str.replaceAll(" ", "");
        str = str.toLowerCase();
        return str;
    }
}
