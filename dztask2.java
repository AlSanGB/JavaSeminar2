// Реализуйте алгоритм сортировки пузырьком числового массива, результат после каждой итерации запишите в лог-файл.
import java.io.IOException;
import java.util.logging.*; 

public class dztask2 {
    //Создание случайного массива целых чисел
    public static Integer[] randIntArray(int n){
        Integer[] arr = new Integer[n];
        Double tempDouble;
        for (int i=0;i<n;i++){
            tempDouble = 100*Math.random();
            arr[i] = tempDouble.intValue();
        }
        return arr;
    };
    
    //Запись массива в лог-файл
    public static void printArray(Integer[] arr, Logger log) {
        int n = arr.length;
        StringBuilder arrayCurrent = new StringBuilder();
        for (int i=0;i<n;i++){
            arrayCurrent.append(arr[i] + " ");
        }
        log.info(arrayCurrent.toString());
    };
    public static void main(String[] args) throws IOException {
        //Инициация логирования
        Logger log = Logger.getLogger(dztask2.class.getName());
        log.setLevel(Level.INFO);
        FileHandler fh = new FileHandler("log.txt");
        log.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();  
        fh.setFormatter(formatter);  
        
        int n = 10;
        Integer[] arr = randIntArray(n);
        int temp = 0;
        printArray(arr, log);
        //Метод пузырька
        for (int i=n;i>1;i--){
            for (int j=0;j<i-1;j++) {
                if (arr[j] > arr[j+1]) {
                    temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
                //Вывод результата шага метода пузырька в лог файл
                printArray(arr, log);
            }
        }
        
    }
}
