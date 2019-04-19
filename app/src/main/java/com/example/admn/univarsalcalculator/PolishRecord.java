
package com.example.admn.univarsalcalculator;

import java.text.DecimalFormat;
import java.util.*;

public class PolishRecord {

    public String stringToResult(String inputString){



        try {
            List listOfValues = string_to_List_conversion(inputString);
            // listOfValues = clutchNumbers(listOfValues);                                                                      //цепляем рядом стоящие числа и десятичные точки
            listOfValues = negativeNumber(listOfValues);                                                                     //обрабатываем отрицательные числа : -1+4 -> (0-1)+4
            listOfValues = sqrt(listOfValues);                                                                               // обработка символа √
            listOfValues = unsignedMultiplication(listOfValues);                                                              //беззнаковое умножение  3( -> 3*(
            listOfValues = polishAlgorithm(listOfValues);                                                                     //перевод в польскую нотацию
            String result = stackMachine(listOfValues);                                                                      //перевод польской нотации в результат матоперации
            if(result.equals("Infinity")){
                result = "∞";
            }
            return result;
        }catch (Exception ex){
            return "Ошибка";
        }
    } // method stringToResult

    private List string_to_List_conversion(String input_string){                                                       //метод разбиения строки на отдельные символы
        List<String> listValues = new LinkedList<String>(Arrays.asList(input_string.split("")));                  //преобразуем строку в лист символов без пробелов

        while(listValues.indexOf("") != -1 || listValues.indexOf(" ") != -1){                                           //удалем пустые строки
            if (listValues.contains(" ")){
                listValues.remove(" ");
            }
            else{
                listValues.remove("");
            }
        }
        listValues = clutchNumbers(listValues);                                                                          //сцепляем числа и точки
        return listValues;
    }//method string_to_Llist_conversion */разбиваем строку на символы/*

    private List clutchNumbers (List<String> ListValues){                                                               //метод соединения чисел и десятичных точек
        int i = 0;
        while (i < ListValues.size()-1){                                                                                //цикл приведения к понятному для аглогитма польской записи виду:работа с числами
            String a = ListValues.get(i);
            String b = ListValues.get(i+1);
            if (a.matches("-?\\d+(\\.\\d+)?") && b.matches("-?\\d+(\\.\\d+)?")){                           //если а и б числа
                a = a.concat(b);                                                                                        //соединяем а и б
                ListValues.set(i,a);                                                                                    //вместо I-того вставляем число
                ListValues.remove(i+1);                                                                           //следующий за ним удаляем
                continue;
            }
            else if(b.equals(",")){                                                                                     //если второй символ точка
                a =  a.concat(b).concat(ListValues.get(i+2));                                                           //соединяем а, точку и следующий символ
                ListValues.set(i,a);                                                                                    //вместо I-того вставляем число
                ListValues.remove(i+1);                                                                           //2 следующих за ними удаляем
                ListValues.remove(i+1);
                continue;
            }
            i++;
        }
        return ListValues;
    }//method clatchNumber */сцепляем числа и точки в одно число/*

    private List negativeNumber (List<String> ListValues){ //обаботка отрицательных исел
        int i = 0;
        while (i < ListValues.size()-1){
            String a = ListValues.get(i);                                                                               // преобразуем -1+4 в (0-1)+4
            String b = ListValues.get(i+1);
            if ((a.equals("(") || a.equals("^")|| a.equals("÷")) && b.equals("-")  ||                                    // если (- или ^- или -x или -(
                    a.equals("-") &&  (b.matches("-?\\d+(\\.\\d+)?") || b.equals("("))&& i==0){

                if (a.equals("(") || a.equals("^")|| a.equals("÷")){
                    ListValues.remove(i+1);                                                                         //удаляем -
                    ListValues.addAll(i+1, string_to_List_conversion("((0-1)×"+ListValues.get(i+1)+")"));//вместо - -> ((0-1)*х)
                    ListValues.remove(i+10);                                                                         //удаляем х, которое осталось дальше по строке
                    i=i+9;                                                                                                 //начинаем проверку после ((0-1)*х)



                }
                else {
                    ListValues.remove(i);                                                                                    //удаляем -
                    ListValues.addAll(i, string_to_List_conversion("(0-1)×"));                                    //вместо - -> ((0-1)*х)
                    i=i+5;                                                                                                    //начинаем проверку после ((0-1)*х)


                }
            }

            i++;
        }
        return ListValues;
    }//method negativeNumber */обработка отрицательных чисел : -х -> (0-x)/*

    private  List sqrt (List<String> ListValues){                                                                       //обработка знака корня
        while (ListValues.contains("√")){                                                                               // пока есть √
            int sqrtIndex = ListValues.indexOf("√");                                                                    //находим индекс корня
            if (ListValues.get(sqrtIndex+1).equals("(")){                                                               //если следущее за √ -> (

                int sumBtackrt = 1;                                                                                     // считаем скобки
                int i = 2;                                                                                              // счётчик индексов внутри корня
                while (sumBtackrt != 0){                                                                                // пока скобки в корне не закончатся
                    String symbol = ListValues.get(sqrtIndex + i);                                                       //получаем символ в корне
                    if(symbol.equals("(")){                                                                              //если символ (
                        sumBtackrt++;                                                                                    //прибавляем счётчик скобок
                    }
                    else if(symbol.equals(")")){                                                                         //если символ )
                        sumBtackrt--;                                                                                    //отнимаем счётчик скобок
                    }
                    i++;                                                                                                 //переход к след символу
                }
                ListValues.set(sqrtIndex,"(");
                ListValues.add(i+sqrtIndex,"^");                                                                 //добавляем после скобки ^
                ListValues.add(i+sqrtIndex+1,"0.5");                                                               // после ^ степень
                ListValues.add(i+sqrtIndex+2,")");
            }
            else{                                                                                                       //иначе за корнем число
                ListValues.set(sqrtIndex,"(");                                                                          //вместо корня скобка
                ListValues.add(sqrtIndex+2,"^");                                                         //после скобки степень
                ListValues.add(sqrtIndex+3,"0.5");                                                       //после степени её значение
                ListValues.add(sqrtIndex+4,")");                                                           //после значения скобка
            }
        }
        return  ListValues;
    } // method sqrt */обработка знака каорня/*

    private List unsignedMultiplication (List<String> ListValues){  // беззнаковое умножение
        int i = 0;
        while ( i < ListValues.size() - 1) {
            String a = ListValues.get(i);                                                                              // преобразуем -1+4 в (0-1)+4
            String b = ListValues.get(i+1);
            if (a.matches("-?\\d+(\\.\\d+)?") && b.equals("(") ||
                    a.equals(")") && (b.matches("-?\\d+(\\.\\d+)?") || b.equals("("))) {                          //если x(   или   )х   или )(  : беззнаковое умножение
                ListValues.add(i + 1, "×");                                                             //довавляем умножение
            }
            i++;
        }

        return ListValues;
    }//method unsignedMultiplication  */беззнаковое умножение/*

    private List<String> polishAlgorithm (List<String> ListofValues){                                               //метод преобразования в польскую нотацию

        List<String>polishListofValues = new LinkedList<String>();
        Stack<String> stack = new Stack<>();
        ListofValues.add(0,"T");                                                                    //обавлем Т для меток начала и конца сортировки
        ListofValues.add("T");
        stack.push("T");                                                                                           //добавляем в стек метку начала

        int i = 1;                                                                                                          //начинаем со второго символа, т.к первый - метка
        while (i < ListofValues.size()){//запускаем цикл преобразования в польскую запись. Алгоритм Дейкстры. https://habr.com/post/100869/
            String symbol = ListofValues.get(i);
            if(symbol.matches("-?\\d+(\\.\\d+)?") ){                                                              //если символ - число
                polishListofValues.add(symbol);                                                                             //записываем его в выходной лист
            }
            else{                                                                                                       //если символ - не число
                if (symbol.equals("+") || symbol.equals("-")) {                                                         //если +-
                    if (stack.peek().equals("T") || stack.peek().equals("(") ){                                          //если в стеке T(
                        stack.push(symbol);                                                                             //символ в стек
                    }
                    else if (stack.peek().equals("+") || stack.peek().equals("-") || stack.peek().equals("×") ||
                            stack.peek().equals("÷")|| stack.peek().equals(("^"))){                                     //если в стеке +-*/ ^
                        polishListofValues.add(stack.pop());                                                               //верх стека в выходной лист
                        continue;                                                                                       //переходим в начало, не добавля шаг i
                    }
                }else {
                    if (symbol.equals("×") || symbol.equals("÷")) {                                                     //если символ */
                        if(stack.peek().equals("T") || stack.peek().equals("+") || stack.peek().equals("-") || stack.peek().equals("(")){           //если в стеке Т+(
                            stack.push(symbol);                                                                         //то символ в стек
                        }
                        else if(stack.peek().equals("×") || stack.peek().equals("÷")|| stack.peek().equals(("^"))){                                  //если в стеке */ ^
                            polishListofValues.add(stack.pop());                                                           //верх стека в выходной лист
                            continue;                                                                                   //переходим в начало, не добавля шаг i
                        }
                    }else  if (symbol.equals("^")){
                        if(stack.peek().equals("T") || stack.peek().equals("+")||stack.peek().equals("-")||
                                stack.peek().equals("×")||  stack.peek().equals("÷")||  stack.peek().equals("(")){
                            stack.push(symbol);

                        }
                        else {
                            polishListofValues.add(stack.pop());
                        }
                    }else if (symbol.equals("(")) {                                                                     //если символ (
                        stack.push(symbol);                                                                             //символ в стек
                    }else if (symbol.equals(")")){                                                                      //если символ )
                        if(stack.peek().equals("+") || stack.peek().equals("-") ||
                                stack.peek().equals("×") || stack.peek().equals("÷")|| stack.peek().equals(("^")))     { //если в стеке +-*/
                            polishListofValues.add(stack.pop());                                                           //верх стека в выходной лист
                            continue;                                                                                   //переходим в начало, не добавля шаг i
                        }else {
                            if (stack.peek().equals("(")) {                                                             //если в стеке (
                                stack.pop();                                                                            // символ в стеке и входной символ удаляем
                                i++;                                                                                    //переходим на другой символ
                                continue;                                                                               //переходим в начало, не добавля шаг i
                            }
                            else if (stack.peek().equals("T")) {                                                        //если в стеке Т
                                polishListofValues.clear();
                                polishListofValues.add("Ошибка");
                                return  polishListofValues;
                            }
                        }
                    }


                    else if(symbol.equals("T")){                                                                       //если смол Т
                        if (stack.peek().equals("+") || stack.peek().equals("-") ||stack.peek().equals("×") ||
                                stack.peek().equals("÷") || stack.peek().equals("^")){//если в стеке +-*/
                            polishListofValues.add(stack.pop());                                                           //верх стека в выходной лист
                            continue;                                                                                   //переходим в начало, не добавля шаг i
                        }
                        else {
                            if (stack.peek().equals("(")) {                                                             //если верх стека (
                                polishListofValues.clear();
                                polishListofValues.add("Ошибка");
                                return  polishListofValues;                                                             //записываем ошибку

                            }
                            else if (stack.peek().equals("T")) {                                                        //если верх стека Т
                                stack.pop();                                                                            //преобазование завершено успешно
                                break;
                            }
                        }
                    }
                }
            }
            i++;
        }//while - конец преобразования в польскую запись
        return polishListofValues;
    }//method polishAlgorithm */Преобразуем лист символов в польскую нотацию/*

    private String stackMachine (List<String> polishListofValues){                                                       //стековая машина - вычисляем выражение по польской нотации
        Stack<String> stack = new Stack<>();
        int i = 0;
        while (i < polishListofValues.size()) {                                                                         //запускаем цикл преобразования записи в результат
            String symbol = polishListofValues.get(i);
            if(symbol.matches("-?\\d+(\\.\\d+)?") ){                                                              //если символ - число
                stack.push(symbol);                                                                                     //аписываем его в стек
            }
            else{                                                                                                       //если не число
                if (symbol.equals("×")){                                                                                //если *
                    stack.push(Double.toString(Double.parseDouble(stack.pop()) * Double.parseDouble(stack.pop())));  //достаём 2 верхних значения из стека, перемножаем, возвращаем результат в стек
                }
                else if(symbol.equals("÷")){                                                                            //если /
                    double a = Double.parseDouble(stack.pop());                                                         //дастаём из стека 1 значение
                    double b = Double.parseDouble(stack.pop());                                                         //достаём из стека значение 2
                    stack.push(Double.toString(b / a));                                                              //делим
                }
                else if(symbol.equals("+")){                                                                            //если +
                    stack.push(Double.toString(Double.parseDouble(stack.pop()) + Double.parseDouble(stack.pop())));  //достаём 2 верхних значения из стека, перемножаем, возвращаем результат в стек
                }
                else if(symbol.equals("-")){                                                                            //если -
                    double a = Double.parseDouble(stack.pop());                                                         //дастаём из стека 1 значение
                    double b = 0;
                    if (stack.empty() == false){                                                                        //если стек не пуст
                        b = Double.parseDouble(stack.pop());                                                             //достаём из стека значение 2
                    }
                    stack.push(Double.toString(b - a));                                                              //вычитаем
                }
                else  if(symbol.equals("^")){
                    double a = Double.parseDouble(stack.pop());                                                          //дастаём из стека 1 значение
                    double b = Double.parseDouble(stack.pop());
                    stack.push(Double.toString(Math.pow(b,a)));
                }
            }
            i++;
        }//конец цикла перевода строки в результат

        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(Double.parseDouble(stack.peek()));                                                                      //оставшееся в стеке - результат операций
    }//method stackMachine */вычисление выражения по польской нотации/*


}// class PolishRecord
