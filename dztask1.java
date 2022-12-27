// Дана строка sql-запроса "select * from students where ". Сформируйте часть WHERE этого запроса, используя StringBuilder. Данные для фильтрации приведены ниже в виде json строки.
// Если значение null, то параметр не должен попадать в запрос.
// Параметры для фильтрации: {"name":"Ivanov", "country":"Russia", "city":"Moscow", "age":"null"}

import java.io.FileReader;
import java.io.IOException;

public class dztask1 {
    public static String getWhere(String filePath)  throws IOException {
        FileReader reader = new FileReader(filePath);
        int c;
        boolean quoteWaitFlag = true;
        boolean nameFlag = true;
        boolean valueFlag = false;
        StringBuilder nameCurrent = new StringBuilder();
        StringBuilder valueCurrent = new StringBuilder();
        StringBuilder whereString = new StringBuilder();
        while((c=reader.read())!=-1){
            //Если встретили любой символ кроме кавычек
            if (c!=34){
                // Если мы в режиме считывания (Не в ожидании кавычек) и сейчас идет считывание имени параметра
                if ((!quoteWaitFlag) && nameFlag) {
                    nameCurrent.append((char)c);
                // Если мы в режиме считывания (Не в ожидании кавычек) и сейчас идет считывание значения параметра
                } else if ((!quoteWaitFlag) && valueFlag) {
                    valueCurrent.append((char)c);
                }
            // Если встретили кавычки
            } else {
                //Если мы были в режиме ожидания кавычек для начала считывания, меняем режим на режим считывания
                if (quoteWaitFlag) {
                    quoteWaitFlag = false;
                //Если мы были в режиме считывания имени, завершаем его и меняем режим на режим ожидания кавычек и режим считывания значения
                } else if ((!quoteWaitFlag) && nameFlag) {
                    quoteWaitFlag = true;
                    nameFlag = false;
                    valueFlag = true;
                //Если мы были в режиме считывания значения, завершаем его, добавляем условие к WHERE и меняем режим на режим ожидания кавычек и считывания имени
                } else if ((!quoteWaitFlag) & valueFlag) {
                    quoteWaitFlag = true;
                    nameFlag = true;
                    valueFlag = false;
                    if (!valueCurrent.toString().equals("null")){
                        if (whereString.length() > 0){
                            whereString.append(" AND ");
                        }
                        whereString.append(nameCurrent.toString() + " = '" + valueCurrent.toString() + "'");
                    }
                    nameCurrent.delete(0, nameCurrent.length());
                    valueCurrent.delete(0, valueCurrent.length());
                }
            }
        }
        reader.close();
        return whereString.toString();
    }
    public static void main(String[] args) throws IOException {
        String sqlQuery = "SELECT * FROM students WHERE";
        String sqlCondition = getWhere("filtercondition.json");
        System.out.print(sqlQuery + " " + sqlCondition);
    }
}