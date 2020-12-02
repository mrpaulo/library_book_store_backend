/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paulo.rodrigues.librarybookstore.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author paulo.rodrigues
 */
public class DateUtils {
    
    public static final String DIAS_DECORRIDOS = "D";
    public static final String DIAS_UTEIS = "U";
    public static final Locale LOCALE_BR = new Locale("pt", "BR");
    

    public static Date addMinutes(Date date, int minutes) {
        if (date != null) {
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(date);
            gc.add(GregorianCalendar.MINUTE, minutes);
            return gc.getTime();
        } else {
            return null;
        }
    }

    public static Date addSeconds(Date date, int seconds) {
        if (date != null) {
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(date);
            gc.add(GregorianCalendar.SECOND, seconds);
            return gc.getTime();
        } else {
            return null;
        }
    }

    /**
     * Retorna uma data sendo o primeiro dia do mes
     *
     * @param date	Instãncia de Date com a data base
     * @return data	Instância de Date com o primeiro dia do mes
     */
    public static Date firstDayMonth(Date date) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        int mes = gc.get(GregorianCalendar.MONTH);
        int ano = gc.get(GregorianCalendar.YEAR);
        gc.set(ano, mes, 1);
        gc.set(GregorianCalendar.HOUR_OF_DAY, 0);
        gc.set(GregorianCalendar.MINUTE, 0);
        gc.set(GregorianCalendar.SECOND, 0);
        gc.set(GregorianCalendar.MILLISECOND, 0);
        return gc.getTime();
    }

    /**
     * Retorna uma data sendo o ultimo dia do mes
     *
     * @param data	Instãncia de Date com a data base
     * @return data	Instância de Date com o ultimo dia do mes
     */
    public static Date lastDayMonth(Date data) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(data);
        int dia = gc.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        int mes = gc.get(GregorianCalendar.MONTH);
        int ano = gc.get(GregorianCalendar.YEAR);
        gc.set(ano, mes, dia);
        gc.set(GregorianCalendar.HOUR_OF_DAY, 23);
        gc.set(GregorianCalendar.MINUTE, 59);
        gc.set(GregorianCalendar.SECOND, 59);
        gc.set(GregorianCalendar.MILLISECOND, 999);
        return gc.getTime();
    }

    /**
     * Retorna a data da próxima segunda-feira após a data passada base
     *
     * @param data	Instãncia de Date com a data base
     * @return data	Instância de Date com a data da próxima segunda
     */
    public static Date nextMonday(Date data) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(data);
        int diaSemana = gc.get(GregorianCalendar.DAY_OF_WEEK);
        int amount = 0;
        //Ajusta o dia para próxima sexta feira
        switch (diaSemana) {
            case GregorianCalendar.MONDAY:
                amount++;
            case GregorianCalendar.TUESDAY:
                amount++;
            case GregorianCalendar.WEDNESDAY:
                amount++;
            case GregorianCalendar.THURSDAY:
                amount++;
            case GregorianCalendar.FRIDAY:
                amount++;
            case GregorianCalendar.SATURDAY:
                amount++;
            case GregorianCalendar.SUNDAY:
                amount++;
        }
        gc.add(GregorianCalendar.DAY_OF_MONTH, amount);
        return gc.getTime();
    }

    /**
     * Retorna a data da próxima sexta feira após a data passada base
     *
     * @param data	Instãncia de Date com a data base
     * @return data	Instância de Date com a data da próxima sexta
     */
    public static Date nextFriday(Date data) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(data);
        int diaSemana = gc.get(GregorianCalendar.DAY_OF_WEEK);
        int amount = 0;
        //Ajusta o dia para próxima sexta feira
        switch (diaSemana) {
            case GregorianCalendar.FRIDAY:
                amount++;
            case GregorianCalendar.SATURDAY:
                amount++;
            case GregorianCalendar.SUNDAY:
                amount++;
            case GregorianCalendar.MONDAY:
                amount++;
            case GregorianCalendar.TUESDAY:
                amount++;
            case GregorianCalendar.WEDNESDAY:
                amount++;
            case GregorianCalendar.THURSDAY:
                amount++;
        }
        gc.add(GregorianCalendar.DAY_OF_MONTH, amount);
        return gc.getTime();
    }

    /**
     * Retorna o numero do ano atual
     *
     * @param date Instância de Date com a data base
     * @return int Integer contendo o numero do ano
     */
    public static int getYear(Date date) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        return gc.get(GregorianCalendar.YEAR);
    }

    public static int getDay(Date date) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        return gc.get(GregorianCalendar.DAY_OF_MONTH);
    }

    /**
     * Retorna o numero do mes atual
     *
     * @param date Instância de Date com a data base
     * @return int Integer contendo o numero do mes
     */
    public static int getMonth(Date date) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        return gc.get(GregorianCalendar.MONTH) + 1;
    }

    /**
     * Retorna o nome do mes por extenso
     *
     * @param mes	inteiro contendo o numero do mes
     * @return string	String contendo o nome do mes
     */
    public static String getNameMonthPt(int mes) {
        switch (mes) {
            case 1:
                return "Janeiro";
            case 2:
                return "Fevereiro";
            case 3:
                return "Março";
            case 4:
                return "Abril";
            case 5:
                return "Maio";
            case 6:
                return "Junho";
            case 7:
                return "Julho";
            case 8:
                return "Agosto";
            case 9:
                return "Setembro";
            case 10:
                return "Outubro";
            case 11:
                return "Novembro";
            case 12:
                return "Dezembro";
        }
        return "";
    }

    /**
     * Retorna o nome do mes abreviado com 3 caracteres
     *
     * @param mes	inteiro contendo o numero do mes
     * @return string	String contendo a abreviação do mes
     */
    public static String getNomeMesAbrev(int mes) {
        switch (mes) {
            case 1:
                return "Jan";
            case 2:
                return "Fev";
            case 3:
                return "Mar";
            case 4:
                return "Abr";
            case 5:
                return "Mai";
            case 6:
                return "Jun";
            case 7:
                return "Jul";
            case 8:
                return "Ago";
            case 9:
                return "Set";
            case 10:
                return "Out";
            case 11:
                return "Nov";
            case 12:
                return "Dez";
        }
        return "";
    }

    /**
     * Retorna uma data com horario zerado
     *
     * @param data	Instância de Date contendo a data e hora
     * @return data	Instância de Date contendo a data com horario zerado
     */
    public static Date removeHour(Date data) {
        if (data != null) {
            GregorianCalendar in = new GregorianCalendar();
            in.setTime(data);
            int dia = in.get(GregorianCalendar.DATE);
            int mes = in.get(GregorianCalendar.MONTH);
            int ano = in.get(GregorianCalendar.YEAR);

            GregorianCalendar out = new GregorianCalendar();
            out.set(ano, mes, dia, 0, 0, 0);
            out.set(GregorianCalendar.MILLISECOND, 0);
            return out.getTime();
        } else {
            return null;
        }
    }

    /**
     * Retorna uma data com segundo zerado
     *
     * @param data	Instância de Date contendo a data e hora
     * @return data	Instância de Date contendo a data com segundo zerado
     */
    public static Date removerSegundo(Date data) {
        if (data != null) {
            GregorianCalendar in = new GregorianCalendar();
            in.setTime(data);
            int dia = in.get(GregorianCalendar.DATE);
            int mes = in.get(GregorianCalendar.MONTH);
            int ano = in.get(GregorianCalendar.YEAR);
            int hora = in.get(GregorianCalendar.HOUR_OF_DAY);
            int minuto = in.get(GregorianCalendar.MINUTE);

            GregorianCalendar out = new GregorianCalendar();
            out.set(ano, mes, dia, hora, minuto, 0);
            out.set(GregorianCalendar.MILLISECOND, 0);
            return out.getTime();
        } else {
            return null;
        }
    }

    public static Date setarHora(Date data, String horaMinuto) {
        if (horaMinuto == null || horaMinuto.trim().isEmpty()) {
            horaMinuto = "00:00";
        }
        if (data != null) {
            GregorianCalendar in = new GregorianCalendar();
            in.setTime(data);
            int dia = in.get(GregorianCalendar.DATE);
            int mes = in.get(GregorianCalendar.MONTH);
            int ano = in.get(GregorianCalendar.YEAR);

            GregorianCalendar out = new GregorianCalendar();
            out.set(ano, mes, dia, Integer.valueOf(horaMinuto.split(":")[0]), Integer.valueOf(horaMinuto.split(":")[1]), horaMinuto.split(":").length > 2 ? Integer.valueOf(horaMinuto.split(":")[2]) : 0);
            out.set(GregorianCalendar.MILLISECOND, 0);
            return out.getTime();
        } else {
            return null;
        }
    }

    public static void removerHora(GregorianCalendar data) {
        data.set(GregorianCalendar.HOUR, 0);
        data.set(GregorianCalendar.MINUTE, 0);
        data.set(GregorianCalendar.SECOND, 0);
        data.set(GregorianCalendar.MILLISECOND, 0);
    }

    /**
     * Retorna a mesma data, setando o horário para 00:00:00.000
     *
     * @param data
     * @return
     */
    public static Date primeiraHora(Date data) {
        if (data == null) {
            return null;
        }
        GregorianCalendar in = new GregorianCalendar();
        in.setTime(data);
        int dia = in.get(GregorianCalendar.DATE);
        int mes = in.get(GregorianCalendar.MONTH);
        int ano = in.get(GregorianCalendar.YEAR);

        GregorianCalendar out = new GregorianCalendar();
        out.set(ano, mes, dia, 0, 0, 0);
        out.set(GregorianCalendar.MILLISECOND, 0);

        return out.getTime();
    }

    /**
     * Retorna a mesma data, setando o horário para 23:59:59.999
     *
     * @param data
     * @return
     */
    public static Date ultimaHora(Date data) {
        if (data == null) {
            return null;
        }
        GregorianCalendar in = new GregorianCalendar();
        in.setTime(data);
        int dia = in.get(GregorianCalendar.DATE);
        int mes = in.get(GregorianCalendar.MONTH);
        int ano = in.get(GregorianCalendar.YEAR);

        GregorianCalendar out = new GregorianCalendar();
        out.set(ano, mes, dia, 23, 59, 59);
        out.set(GregorianCalendar.MILLISECOND, 999);

        return out.getTime();
    }

    /**
     * Retorna a parte de hora de uma data
     *
     * @param data
     * @return
     */
    public static double extrairHora(Date data) {
        GregorianCalendar in = new GregorianCalendar();
        in.setTime(data);

        long hora = in.get(GregorianCalendar.HOUR_OF_DAY);
        long minuto = Math.round(in.get(GregorianCalendar.MINUTE) / 0.6);

        return Double.valueOf(hora + "." + FormatUtils.formatDoisZeros(minuto));
    }

    /**
     * Atalho para diferencaDias(Date de, Date ate, String tipoDia,
     * List&lt;Date&gt; diasNaoUteis) que automaticamente assume <i>null</i>
     * para o vetor diasNaoUteis, dispensando a passagem deste vetor a partir do
     * método chamador.
     *
     * @param de	Instância de Date coma data inicial
     * @param ate	Instância de Date coma data final
     * @param tipoDia
     * {@link br.srv.lehnen.nfse.util.NfseConst#DIAS_UTEIS Dias úteis} ou
     * {@link br.srv.lehnen.nfse.util.NfseConst#DIAS_DECORRIDOS total de dias do período}.
     * @return dias	Número de dias entre as duas datas
     */
    public static int diferencaDias(Date de, Date ate, String tipoDia) {
        return diferencaDias(de, ate, tipoDia, null);
    }

    /**
     * Retorna a diferença entre duas datas, desconsiderando a hora
     *
     * @param de	Instância de Date coma data inicial
     * @param ate	Instância de Date coma data final
     * @param tipoDia
     * {@link br.srv.lehnen.nfse.util.NfseConst#DIAS_UTEIS Dias úteis} ou
     * {@link br.srv.lehnen.nfse.util.NfseConst#DIAS_DECORRIDOS total de dias do período}.
     * @param diasNaoUteis	Instância de List<Date> contendo lista de feriados no
     * período. Passar <i>null</i> caso não queira descosiderar esta informação
     * ou se ela não estiver disponível.
     * @return dias	Número de dias entre as duas datas
     */
    public static int diferencaDias(Date de, Date ate, String tipoDia, List<Date> diasNaoUteis) {
        int result = 0;
        if ((de != null) && (ate != null) && (tipoDia != null)) {
            //Considera apenas dias e não diferença de horas
            de = removeHour(de);
            ate = removeHour(ate);

            GregorianCalendar gcDe = new GregorianCalendar();
            gcDe.setTime(de);

            GregorianCalendar gcAte = new GregorianCalendar();
            gcAte.setTime(ate);

            while (gcDe.before(gcAte)) {
                gcDe.add(GregorianCalendar.DATE, 1);
                if (tipoDia.equals(DIAS_DECORRIDOS)) {
                    result++;
                } else {
                    if (isDiaUtil(gcAte, diasNaoUteis)) {
                        result++;
                    }
                }
            }
        }
        return result;
    }

   

    /**
     * Retorna se a data não é Sábado ou Domingo e não está na lista de
     * diasNaoUteis
     *
     * @param gc	Instância de GregorianCalendar com a data
     * @param diasNaoUteis	Instância de List<Date> com os dias não úteis
     * (opcional)
     * @return booleano	True se dia útil. False se não
     */
    static boolean isDiaUtil(GregorianCalendar gc, List<Date> diasNaoUteis) {
        boolean result = ((gc.get(GregorianCalendar.DAY_OF_WEEK) != GregorianCalendar.SATURDAY) && (gc.get(GregorianCalendar.DAY_OF_WEEK) != GregorianCalendar.SUNDAY));
        if ((result) && (diasNaoUteis != null) && (diasNaoUteis.size() > 0)) {
            Iterator<Date> iterator = diasNaoUteis.iterator();
            while ((result) && (iterator.hasNext())) {
                Date data = iterator.next();
                if (removeHour(data).equals(removeHour(gc.getTime()))) {
                    result = false;
                }
            }
        }
        return result;
    }

    public static boolean isDiaUtil(Date date) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        boolean result = ((gc.get(GregorianCalendar.DAY_OF_WEEK) != GregorianCalendar.SATURDAY) && (gc.get(GregorianCalendar.DAY_OF_WEEK) != GregorianCalendar.SUNDAY));
        return result;
    }

    public static int getSemanaAno(Date data) {
        if (data == null) {
            return 0;
        }
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(data);
        return gc.get(GregorianCalendar.WEEK_OF_YEAR);
    }

    public static int getSemanaMes(Date data) {
        if (data == null) {
            return 0;
        }
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(data);
        gc.setFirstDayOfWeek(GregorianCalendar.SUNDAY);
        return gc.get(GregorianCalendar.WEEK_OF_MONTH);
    }

    public static String getDescricaoSemana(Date data) {
        if (data == null) {
            return "";
        }
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(data);
        gc.setFirstDayOfWeek(GregorianCalendar.SUNDAY);
        return getNameMonthPt(gc.get(GregorianCalendar.MONTH) + 1) + " de " + gc.get(GregorianCalendar.YEAR) + ", semana " + gc.get(GregorianCalendar.WEEK_OF_MONTH);
    }
    private static List<Integer> diasUteisPadrao;

    /**
     * Retorna a lista de dias uteis de segunda a sexta
     *
     * @return {GregorianCalendar.MONDAY, GregorianCalendar.TUESDAY,
     * GregorianCalendar.WEDNESDAY, GregorianCalendar.THURSDAY,
     * GregorianCalendar.FRIDAY}
     *
     * @see #getHoras(java.util.Date, java.util.Date, double)
     */
    public static List<Integer> getDiasUteisPadrao() {
        if (diasUteisPadrao == null) {
            diasUteisPadrao = new ArrayList<Integer>();
            diasUteisPadrao.add(GregorianCalendar.MONDAY);
            diasUteisPadrao.add(GregorianCalendar.TUESDAY);
            diasUteisPadrao.add(GregorianCalendar.WEDNESDAY);
            diasUteisPadrao.add(GregorianCalendar.THURSDAY);
            diasUteisPadrao.add(GregorianCalendar.FRIDAY);
        }
        return diasUteisPadrao;
    }

    public static Date adicionarHoras(Date data, double horas, double horasUteis, List<Date> feriados) {
        return adicionarHoras(data, horas, horasUteis, getDiasUteisPadrao(), feriados);
    }

    public static Date adicionarHoras(Date data, double horas, double horasUteis) {
        return adicionarHoras(data, horas, horasUteis, getDiasUteisPadrao(), null);
    }

    public static Date adicionarHoras(Date data, double horas, double horasUteis, List<Integer> diasUteis, List<Date> feriados) {
        if (data != null && horasUteis > 0) {
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(data);
            while (horas > horasUteis) {
                gc.add(GregorianCalendar.DAY_OF_MONTH, 1);
                while (!isDiaUtil(gc.getTime(), diasUteis) || isDiaFeriado(gc.getTime(), feriados)) {
                    gc.add(GregorianCalendar.DAY_OF_MONTH, 1);
                }
                horas -= horasUteis;
            }
            return gc.getTime();
        } else {
            return null;
        }
    }

    public static double adicionarHoras(Date dataInicio, Date dataFim, double horasUteis, List<Date> feriados) {
        return getHoras(dataInicio, dataFim, horasUteis, getDiasUteisPadrao(), feriados);
    }

    /**
     * Retorna a quantidade de horas entre duas datas, considerando apenas dias
     * uteis (seg-sex) e a quantidade de horas que serão trabalhadas por dia.
     *
     * @param dataInicio
     * @param dataFim
     * @param horasUteis horas que serao trabalhadas por dia
     * @return
     */
    public static double getHoras(Date dataInicio, Date dataFim, double horasUteis) {
        return getHoras(dataInicio, dataFim, horasUteis, getDiasUteisPadrao(), null);
    }

    /**
     * Retorna a quantidade de horas entre duas datas, considerando apenas os
     * dias da semana passados e ignorando feriados, e a quantidade de horas que
     * serão trabalhadas por dia
     *
     * @param dataInicio
     * @param dataFim
     * @param horasUteis horas que serao trabalhadas por dia
     * @param diasUteis dias da semana. Exemplo: {GregorianCalendar.MONDAY,
     * GregorianCalendar.TUESDAY, ...}
     * @param feriados datas que são feriado
     * @return
     */
    public static double getHoras(Date dataInicio, Date dataFim, double horasUteis, List<Integer> diasUteis, List<Date> feriados) {
        if (dataInicio != null && dataFim != null && horasUteis > 0) {
            dataFim = removeHour(dataFim);
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(removeHour(dataInicio));
            double result = 0;
            while (gc.getTime().equals(dataFim) || gc.getTime().before(dataFim)) {
                gc.add(GregorianCalendar.DAY_OF_MONTH, 1);
                while (!isDiaUtil(gc.getTime(), diasUteis) || isDiaFeriado(gc.getTime(), feriados)) {
                    gc.add(GregorianCalendar.DAY_OF_MONTH, 1);
                }
                result += horasUteis;
            }
            return result;
        } else {
            return 0;
        }

    }

    public static boolean isDiaUtil(Date d, List<Integer> diasUteis) {
        if (d != null && diasUteis != null) {
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(d);
            int diaSemana = gc.get(GregorianCalendar.DAY_OF_WEEK);

            if (diasUteis.size() > 0) {
                return diasUteis.contains(diaSemana);
            } else {
                return (diaSemana != 0 && diaSemana != 7);
            }
        } else {
            return false;
        }
    }

    public static boolean isDiaFeriado(Date d, List<Date> feriados) {
        if (d != null && feriados != null) {
            boolean result = false;
            Iterator<Date> iterator = feriados.iterator();
            d = removeHour(d);
            while ((!result) && (iterator.hasNext())) {
                Date feriado = iterator.next();
                if (feriado != null) {
                    feriado = removeHour(feriado);
                    result = (d.equals(feriado));
                }
            }
            return result;
        } else {
            return false;
        }
    }

    public static Date selecionarMaior(Date alvo, Date teste) {
        if (teste != null) {
            if (alvo == null) {
                return teste;
            } else {
                alvo = removeHour(alvo);
                teste = removeHour(teste);
                if (teste.after(alvo)) {
                    return teste;
                } else {
                    return alvo;
                }
            }
        } else {
            return alvo;
        }
    }

    public static int getDiaDaSemana(Date data) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(data);
        gc.setFirstDayOfWeek(Calendar.SUNDAY);
        return gc.get(Calendar.DAY_OF_WEEK);
    }

    public static Date selecionarMenor(Date alvo, Date teste) {
        if (teste != null) {
            if (alvo == null) {
                return teste;
            } else {
                alvo = removeHour(alvo);
                teste = removeHour(teste);
                if (teste.before(alvo)) {
                    return teste;
                } else {
                    return alvo;
                }
            }
        } else {
            return alvo;
        }
    }

    public static Date adicionarDiasUteis(Date data, int dias, List<Date> feriados) {
        return adicionarDiasUteis(data, dias, getDiasUteisPadrao(), feriados);
    }

    public static Date adicionarDiasUteis(Date data, int dias, List<Integer> diasUteis, List<Date> feriados) {
        if (data == null || diasUteis == null || diasUteis.isEmpty()) {
            return data;
        } else {
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(data);
            if (dias > 0) {
                do {
                    gc.add(GregorianCalendar.DAY_OF_MONTH, 1);
                    data = gc.getTime();
                    boolean diaUtil = isDiaUtil(data, diasUteis) && !isDiaFeriado(data, feriados);
                    if (diaUtil) {
                        dias--;
                    }
                } while (dias > 0);
            } else if (dias < 0) {
                do {
                    gc.add(GregorianCalendar.DAY_OF_MONTH, -1);
                    data = gc.getTime();
                    boolean diaUtil = isDiaUtil(data, diasUteis) && !isDiaFeriado(data, feriados);
                    if (diaUtil) {
                        dias++;
                    }
                } while (dias < 0);
            }

            return data;
        }
    }

    public static Date adicionarDias(Date data, int dias) {
        if (data == null) {
            return null;
        }
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(data);
        gc.add(GregorianCalendar.DAY_OF_MONTH, dias);
        data = gc.getTime();
        return data;

    }

    public static Date adicionarHoras(Date dataHora, int horas) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(dataHora);
        gc.add(GregorianCalendar.HOUR_OF_DAY, horas);
        dataHora = gc.getTime();
        return dataHora;
    }

    /**
     * Retorna o texto descritivo a partir da comparação das datas de inicio e
     * fim previstas e realizadas para uma tarefa, etapa ou ação fiscal.
     * <br>todos os parametros podem receber NULL
     *
     * @param retornarCSS se True, retorna o código de cor (padrão CSS #RGB)
     * para renderização do texto
     * @param dataPrevistaInicio data onde deveria ter sido iniciada
     * @param dataPrevistaFim data onde deveria ter sido terminada
     * @param dataRealInicio data onde foi iniciada
     * @param dataRealFim data onde foi terminada
     * @return Texto a ser exibido ao usuário
     */
    public static String getDescricaoAtraso(boolean retornarCodigoCor, Date dataPrevistaInicio, Date dataPrevistaFim, Date dataRealInicio, Date dataRealFim) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
        Date hoje = new Date();
        //Se ja foi terminada
        if (dataRealFim != null) {
            if (dataPrevistaFim != null) {
                if (dataRealFim.after(dataPrevistaFim)) {
                    return retornarCodigoCor ? "#227A5A" : retornarCodigoCor ? "#000000" : "Terminada com atraso de " + diferencaDias(dataPrevistaFim, dataRealFim, DIAS_DECORRIDOS) + " dias úteis";
                }
                if (dataRealFim.before(dataPrevistaFim)) {
                    return retornarCodigoCor ? "#22B98E" : "Terminada " + diferencaDias(dataRealFim, dataPrevistaFim, DIAS_DECORRIDOS) + " dias úteis antes do prazo";
                }
                return retornarCodigoCor ? "#22A55A" : "Terminada no dia previsto";
            }
            return retornarCodigoCor ? "#22A55A" : "Terminada em: " + sdf.format(dataRealFim);
        }

        //Se ja deveria ter sido terminada
        if (dataPrevistaFim != null) {
            if (hoje.after(dataPrevistaFim)) {
                return retornarCodigoCor ? "#ff0000" : "Atrasada: já deveria ter sido terminada";
            }
        }

        //Se ja foi iniciada ou se deveria ter sido iniciada
        if (dataPrevistaInicio != null) {
            //tem data prevista
            if (dataRealInicio == null) {
                //não iniciou
                if (hoje.after(dataPrevistaInicio)) {
                    return retornarCodigoCor ? "#770000" : "Atrasada: já deveria ter sido iniciada";
                } else {
                    return retornarCodigoCor ? "#000000" : "Aguardando início";
                }

            } else {
                //ja iniciou
                if (dataRealInicio.after(dataPrevistaInicio)) {
                    return retornarCodigoCor ? "#8548E9" : "Iniciada com atraso de " + diferencaDias(dataPrevistaInicio, dataRealInicio, DIAS_DECORRIDOS) + " dias úteis";
                }
                if (dataRealInicio.before(dataPrevistaInicio)) {
                    return retornarCodigoCor ? "#008AE9" : "Iniciada " + diferencaDias(dataRealInicio, dataPrevistaInicio, DIAS_DECORRIDOS) + " dias úteis antes do prazo";
                }
                return retornarCodigoCor ? "#008AE9" : "Iniciada no dia previsto";
            }
        }
        return retornarCodigoCor ? "#008AE9" : "";
    }

    public static String getDateForSQL(Date d) {
        if (d != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(d);
        } else {
            return "null";
        }
    }

    /**
     * Converte um objeto do tipo Date para uma String contendo a Data no
     * formato Brasileiro
     *
     * @param d Objeto Date a ser convertido
     * @return
     */
    public static String getDateBrFormat(Date d) {
        if (d != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            return sdf.format(d);
        } else {
            return "null";
        }
    }

    public static Date getDateBrFormat(String s) {
        if (s != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            try {
                return sdf.parse(s);
            } catch (ParseException ex) {
                Logger.getLogger(DateUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public static Date getDateEnFormat(String s) {
        if (s != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                return sdf.parse(s);
            } catch (ParseException ex) {
                Logger.getLogger(DateUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public static Date getDateEnFormatHour(String s) {
        if (s != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                return sdf.parse(s);
            } catch (ParseException ex) {
                Logger.getLogger(DateUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public static Date getDateBrFormatHour(String s) {
        if (s != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            try {
                return sdf.parse(s);
            } catch (ParseException ex) {
                Logger.getLogger(DateUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public static String enToBRFormat(String dateEN) {
        Date d = getDateEnFormat(dateEN);
        if (d != null) {
            return getDateForString(d);
        }
        return null;
    }

    public static String getDateEnFormatToString(Date d) {
        if (d != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(d);
        }
        return null;
    }

    public static String getDateForString(Date d) {
        if (d != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.format(d);
        } else {
            return "";
        }
    }

    public static String getTimestampForString(Date d) {
        if (d != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            return sdf.format(d);
        } else {
            return "";
        }
    }

    public static String getTimestampWithSecondsForString(Date d) {
        if (d != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            return sdf.format(d);
        } else {
            return "";
        }
    }

    public static String getTimestampToSQL(Date d) {
        if (d != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(d);
        } else {
            return "";
        }
    }

    /**
     * Converte um objeto do tipo Date para uma String contendo a Data no
     * formato Americano
     *
     * @param d Objeto Date a ser convertido
     * @return
     */
    public static String getDateUsFormat(Date d) {
        if (d != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
            return sdf.format(d);
        } else {
            return "null";
        }
    }

    public static String getExtenso(Date data) {
        if (data != null) {
            StringBuilder sb = new StringBuilder();
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(data);
            sb.append(gc.get(GregorianCalendar.DAY_OF_MONTH));
            sb.append(" dias do mês de ");
            sb.append(getNameMonthPt(gc.get(GregorianCalendar.MONTH) + 1).toLowerCase());
            sb.append(" do ano de ");
            sb.append(gc.get(GregorianCalendar.YEAR));
            return sb.toString();
        } else {
            return "";
        }
    }

    public static String getExtensoDataHora(Date data) {
        if (data != null) {
            StringBuilder sb = new StringBuilder();
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(data);
            sb.append(gc.get(GregorianCalendar.DAY_OF_MONTH));
            sb.append(" dias do mês de ");
            sb.append(getNameMonthPt(gc.get(GregorianCalendar.MONTH) + 1).toLowerCase());
            sb.append(" do ano de ");
            sb.append(gc.get(GregorianCalendar.YEAR));
            sb.append(", às ");
            sb.append(gc.get(GregorianCalendar.HOUR_OF_DAY));
            sb.append(":");
            int minuto = gc.get(GregorianCalendar.MINUTE);
            if (minuto < 10) {
                sb.append("0").append(minuto);
            } else {
                sb.append(minuto);
            }
            sb.append(" horas");
            return sb.toString();
        } else {
            return "";
        }
    }

    public static String getDataPorExtenso(Date data) {
        if (data != null) {
            StringBuilder sb = new StringBuilder();
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(data);
            sb.append(gc.get(GregorianCalendar.DAY_OF_MONTH));
            sb.append(" de ");
            sb.append(getNameMonthPt(gc.get(GregorianCalendar.MONTH) + 1).toLowerCase());
            sb.append(" de ");
            sb.append(gc.get(GregorianCalendar.YEAR));
            return sb.toString();
        } else {
            return "";
        }
    }

    public static String getDataHoraPorExtenso(Date data) {
        if (data != null) {
            StringBuilder sb = new StringBuilder();
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(data);
            sb.append(gc.get(GregorianCalendar.DAY_OF_MONTH));
            sb.append(" de ");
            sb.append(getNameMonthPt(gc.get(GregorianCalendar.MONTH) + 1).toLowerCase());
            sb.append(" de ");
            sb.append(gc.get(GregorianCalendar.YEAR));
            sb.append(", às ");
            sb.append(gc.get(GregorianCalendar.HOUR_OF_DAY));
            sb.append(":");
            int minuto = gc.get(GregorianCalendar.MINUTE);
            if (minuto < 10) {
                sb.append("0").append(minuto);
            } else {
                sb.append(minuto);
            }
            sb.append(" horas");
            return sb.toString();
        } else {
            return "";
        }
    }

    public static Date adicionarMes(Date data, int meses) {
        if (data != null) {
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(data);
            gc.add(GregorianCalendar.MONTH, meses);
            return gc.getTime();
        } else {
            return null;
        }

    }

    public static Date adicionarAno(Date data, int anos) {
        if (data != null) {
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(data);
            gc.add(GregorianCalendar.YEAR, anos);
            return gc.getTime();
        } else {
            return null;
        }

    }

    public static Date getDiaMesSubsequente(int dia, Date d) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(d);
        gc.set(GregorianCalendar.DAY_OF_MONTH, dia);
        gc.set(GregorianCalendar.HOUR, 0);
        gc.set(GregorianCalendar.MINUTE, 0);
        gc.set(GregorianCalendar.SECOND, 0);
        gc.set(GregorianCalendar.MILLISECOND, 0);
        gc.add(GregorianCalendar.MONTH, 1);
        return gc.getTime();
    }

    public static Date getMesSubsequente(Date d) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(d);
        gc.set(GregorianCalendar.DAY_OF_MONTH, 1);
        gc.set(GregorianCalendar.HOUR, 0);
        gc.set(GregorianCalendar.MINUTE, 0);
        gc.set(GregorianCalendar.SECOND, 0);
        gc.set(GregorianCalendar.MILLISECOND, 0);
        gc.add(GregorianCalendar.MONTH, 1);
        return gc.getTime();
    }

    public static Date getMesAnterior(Date d) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(d);
        gc.set(GregorianCalendar.DAY_OF_MONTH, 1);
        gc.set(GregorianCalendar.HOUR, 0);
        gc.set(GregorianCalendar.MINUTE, 0);
        gc.set(GregorianCalendar.SECOND, 0);
        gc.set(GregorianCalendar.MILLISECOND, 0);
        gc.add(GregorianCalendar.MONTH, -1);
        return gc.getTime();
    }

    public static String getAnoMesSubsequente(Date d) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(getMesSubsequente(d));
        return getAnoMes(gc.get(GregorianCalendar.YEAR), gc.get(GregorianCalendar.MONTH) + 1);
    }

    public static String getAnoMesAnterior(Date d) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(getMesAnterior(d));
        return getAnoMes(gc.get(GregorianCalendar.YEAR), gc.get(GregorianCalendar.MONTH) + 1);
    }

    public static String getAnoMesAnterior(String anomes) {
        return getAnoMesAnterior(primeiroDia(anomes));
    }

    public static String getAnoMesPosterior(String anomes) {
        return getAnoMesSubsequente(primeiroDia(anomes));
    }

    public static String getAnoMes(GregorianCalendar gc) {
        return getAnoMes(gc.get(GregorianCalendar.YEAR), gc.get(GregorianCalendar.MONTH) + 1);
    }

    public static String getAnoMes(Date d) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(d);
        return getAnoMes(gc.get(GregorianCalendar.YEAR), gc.get(GregorianCalendar.MONTH) + 1);
    }

    public static String getAnoMes(int ano, int mes) {
        return ano + DateUtils.formatLong(mes, 2);
    }

    public static int getMesPorNome(String mes) {
        switch (mes) {
            case "Janeiro":
                return 1;
            case "Fevereiro":
                return 2;
            case "Março":
                return 3;
            case "Abril":
                return 4;
            case "Maio":
                return 5;
            case "Junho":
                return 6;
            case "Julho":
                return 7;
            case "Agosto":
                return 8;
            case "Setembro":
                return 9;
            case "Outubro":
                return 10;
            case "Novembro":
                return 11;
            case "Dezembro":
                return 12;
            default:
                return 0;
        }
    }

    /**
     * Retorna um long com zeros a esquerda
     *
     * @param valor long contendo o valor
     * @param zerosEsquerda inteiro com o tamanho do valor formatado
     * @return string String contendo o valor com zeros a esquerda
     */
    public static String formatLong(long valor, int zerosEsquerda) {
        StringBuilder sb = new StringBuilder();
        String sv = Long.toString(valor);
        int diferenca = zerosEsquerda - sv.length();
        for (int i = 1; i <= diferenca; i++) {
            sb.append('0');
        }
        sb.append(sv);
        return sb.toString();
    }

    public static Date primeiroDiaMes(String anomes) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.set(getAno(anomes), getMes(anomes) - 1, 1);

        gc.set(GregorianCalendar.HOUR, 0);
        gc.set(GregorianCalendar.MINUTE, 0);
        gc.set(GregorianCalendar.SECOND, 0);
        gc.set(GregorianCalendar.MILLISECOND, 0);
        return gc.getTime();
    }

    public static Date primeiroDiaMes(int ano, int mes) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.set(ano, mes - 1, 1);
        gc.set(GregorianCalendar.HOUR, 0);
        gc.set(GregorianCalendar.MINUTE, 0);
        gc.set(GregorianCalendar.SECOND, 0);
        gc.set(GregorianCalendar.MILLISECOND, 0);
        return gc.getTime();
    }

    public static Date ultimoDiaMes(int ano, int mes) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.set(ano, mes - 1, 1);
        int dia = gc.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        gc.set(ano, mes - 1, dia);
        gc.set(GregorianCalendar.HOUR, 0);
        gc.set(GregorianCalendar.MINUTE, 0);
        gc.set(GregorianCalendar.SECOND, 0);
        gc.set(GregorianCalendar.MILLISECOND, 0);
        return gc.getTime();
    }

    public static String getNomeMesAnoAbrevFromData(Date data) {
        StringBuilder sb = new StringBuilder();
        if (data != null) {
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(data);
            sb.append(getNomeMesAbrev(gc.get(GregorianCalendar.MONTH) + 1));
            sb.append("/");
            sb.append(gc.get(GregorianCalendar.YEAR));
        }
        return sb.toString();
    }

    public static String getNomeMesAnoFromData(Date data) {
        StringBuilder sb = new StringBuilder();
        if (data != null) {
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(data);
            sb.append(getNameMonthPt(gc.get(GregorianCalendar.MONTH) + 1));
            sb.append("/");
            sb.append(gc.get(GregorianCalendar.YEAR));
        }
        return sb.toString();
    }

    public static int getUltimoDiaDoMes(int ano, int mes) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.set(ano, mes - 1, 1);
        return getUltimoDiaDoMes(gc);
    }

    public static int getUltimoDiaDoMes(GregorianCalendar gc) {
        int dia = gc.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        return dia;
    }

    public static int getUltimoDiaDoMes(Date data) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(data);
        return getUltimoDiaDoMes(gc);
    }

    public static Date setDiaDoMes(Date data, int dia) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(data);
        gc.set(GregorianCalendar.DAY_OF_MONTH, dia);
        return gc.getTime();
    }

    public static Date getUltimoDiaDoMesAsDate(Date data) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(data);
        gc.set(GregorianCalendar.DAY_OF_MONTH, gc.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));
        gc.set(GregorianCalendar.HOUR_OF_DAY, 23);
        gc.set(GregorianCalendar.MINUTE, 59);
        gc.set(GregorianCalendar.SECOND, 59);
        gc.set(GregorianCalendar.MILLISECOND, 999);
        return gc.getTime();
    }

    public static boolean isMesmaData(Date data1, Date data2) {
        GregorianCalendar gc1 = new GregorianCalendar();
        GregorianCalendar gc2 = new GregorianCalendar();
        gc1.setTime(data1);
        gc2.setTime(data2);
        return (gc1.get(GregorianCalendar.YEAR) == gc2.get(GregorianCalendar.YEAR)) && (gc1.get(GregorianCalendar.MONTH) == gc2.get(GregorianCalendar.MONTH)) && (gc1.get(GregorianCalendar.DAY_OF_MONTH) == gc2.get(GregorianCalendar.DAY_OF_MONTH));
    }

    public static Date primeiroDia(String anoMes) {
        if ((anoMes != null) && (anoMes.length() == 6)) {
            GregorianCalendar gc = new GregorianCalendar();
            gc.set(GregorianCalendar.DATE, 1);
            gc.set(GregorianCalendar.MONTH, Integer.parseInt(anoMes.substring(4)) - 1);
            gc.set(GregorianCalendar.YEAR, Integer.parseInt(anoMes.substring(0, 4)));
            return gc.getTime();
        } else {
            return null;
        }
    }

    public static Date ultimoDia(String anoMes) {
        if ((anoMes != null) && (anoMes.length() == 6)) {
            GregorianCalendar gc = new GregorianCalendar();
            gc.set(GregorianCalendar.DATE, 1);
            gc.set(GregorianCalendar.MONTH, Integer.parseInt(anoMes.substring(4)) - 1);
            gc.set(GregorianCalendar.YEAR, Integer.parseInt(anoMes.substring(0, 4)));

            gc.add(GregorianCalendar.MONTH, 1);
            gc.add(GregorianCalendar.DATE, -1);

            return gc.getTime();
        } else {
            return null;
        }
    }

    public static Date somaData(Date data, int somaDias) {
        SimpleDateFormat d = new SimpleDateFormat("dd");
        SimpleDateFormat m = new SimpleDateFormat("MM");
        SimpleDateFormat y = new SimpleDateFormat("yyyy");

        int dia = Integer.parseInt(d.format(data));
        int mes = Integer.parseInt(m.format(data));
        int ano = Integer.parseInt(y.format(data));

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Calendar calendar = new GregorianCalendar(ano, mes, dia);
        Date dt = new Date();
        try {
            dt = data;
            calendar.setTime(data);
            calendar.add(Calendar.DATE, somaDias);
        } catch (Exception e) {
            Logger.getLogger(DateUtils.class.getName()).log(Level.SEVERE, "Erro ao somar datas: " + data + " + " + somaDias + " dias: " + e.getMessage(), e);
        }

        return calendar.getTime();
    }

    public static Date somaData(Date data, int somaDias, String tipoDia) {
        SimpleDateFormat d = new SimpleDateFormat("dd");
        SimpleDateFormat m = new SimpleDateFormat("MM");
        SimpleDateFormat y = new SimpleDateFormat("yyyy");

        int dia = Integer.parseInt(d.format(data));
        int mes = Integer.parseInt(m.format(data));
        int ano = Integer.parseInt(y.format(data));

        int total = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        GregorianCalendar calendar = new GregorianCalendar(ano, mes, dia);
        Date dt = new Date();
        if (tipoDia.equals(DIAS_DECORRIDOS)) {
            try {
                dt = data;
                calendar.setTime(data);
                calendar.add(Calendar.DATE, somaDias);
            } catch (Exception e) {
                Logger.getLogger(DateUtils.class.getName()).log(Level.SEVERE, "Erro ao somar datas: " + data + " + " + somaDias + " dias " + tipoDia + ":" + e.getMessage(), e);

            }
        } else {
            while (somaDias > 0) {
                if (isDiaUtil(calendar, null)) {
                    somaDias--;
                }
                calendar.add(GregorianCalendar.DATE, 1);
                total++;
            }
        }
        return calendar.getTime();
    }

    public static boolean isDataNoPeriodo(Date data, Date inicio, Date fim) {
        if (data == null) {
            return false;
        }
        data = removeHour(data);
        boolean ok = true;
        if (inicio != null) {
            inicio = removeHour(inicio);
            ok = ok && (data.equals(inicio) || data.after(inicio));
        }
        if (fim != null) {
            fim = removeHour(fim);
            ok = ok && (data.equals(fim) || data.before(fim));
        }
        return ok;
    }

    public static int getAnoAtual() {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(new Date());
        return gc.get(GregorianCalendar.YEAR);
    }

    public static int getMesAtual() {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(new Date());
        return gc.get(GregorianCalendar.MONTH) + 1;
    }

    public static String getAnoMesAtual() {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(new Date());
        return getAnoMes(gc.get(GregorianCalendar.YEAR), gc.get(GregorianCalendar.MONTH) + 1);
    }

    public static Date getDate(int dia, int mes, int ano) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.set(GregorianCalendar.DAY_OF_MONTH, dia);
        gc.set(GregorianCalendar.MONTH, mes - 1);
        gc.set(GregorianCalendar.YEAR, ano);

        gc.set(GregorianCalendar.HOUR, 0);
        gc.set(GregorianCalendar.MINUTE, 0);
        gc.set(GregorianCalendar.SECOND, 0);
        gc.set(GregorianCalendar.MILLISECOND, 0);

        return gc.getTime();
    }

    public static String getDoubleAsString(double d) {
        long[] horaMinuto = getHoraMinuto(d);
        StringBuilder sb = new StringBuilder();
        sb.append(format(horaMinuto[0]));
        sb.append(":");
        sb.append(format(horaMinuto[1]));
        return sb.toString();
    }

    private static long[] getHoraMinuto(double d) {
        if (d < 0) {
            d = d * -1;
        }
        long[] horaMinuto = {0, 0};
        long i = Math.round(d * 100);
        while (i >= 100) {
            horaMinuto[0]++;
            i -= 100;
        }

        horaMinuto[1] = Math.round(i * 0.6);
        return horaMinuto;
    }

    private static String format(long i) {
        if (i < 10) {
            return "0" + i;
        } else {
            return Long.toString(i);
        }
    }

    public static boolean dataDentroMes(Date data, String anomes) {
        int ano = Integer.valueOf(anomes.substring(0, 4));
        int mes = Integer.valueOf(anomes.substring(4));
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(data);
        if (ano == gc.get(GregorianCalendar.YEAR) && mes == (1 + gc.get(GregorianCalendar.MONTH))) {
            return true;
        }
        return false;
    }

    /**
     * Ajusta o parametro de filtro "datainicial" para uma query em um campo do
     * tipo timestamp
     *
     * @param data	Instancia de Date com a data base
     * @return data	Instancia de Date com o primeiro dia do mes
     */
    public static Date ajustarDataInicialParaQuery(Date data) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(data);
        gc.set(GregorianCalendar.HOUR_OF_DAY, 0);
        gc.set(GregorianCalendar.MINUTE, 0);
        gc.set(GregorianCalendar.SECOND, 0);
        gc.set(GregorianCalendar.MILLISECOND, 0);
        return gc.getTime();
    }

    /**
     * Ajusta o parametro de filtro "datafinal" para uma query em um campo do
     * tipo timestamp
     *
     * @param data	Instancia de Date com a data base
     * @return data	Instancia de Date com o ultimo dia do mes
     */
    public static Date ajustarDataFinalParaQuery(Date data) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(data);
        gc.set(GregorianCalendar.HOUR_OF_DAY, 23);
        gc.set(GregorianCalendar.MINUTE, 59);
        gc.set(GregorianCalendar.SECOND, 59);
        gc.set(GregorianCalendar.MILLISECOND, 999);
        return gc.getTime();
    }

    public static int diferencaMeses(Date de, Date ate) {
        GregorianCalendar gcDe = new GregorianCalendar();
        gcDe.setTime(de);
        GregorianCalendar gcAte = new GregorianCalendar();
        gcAte.setTime(ate);
        return diferencaMeses(gcDe, gcAte);
    }

    public static int diferencaMeses(GregorianCalendar de, GregorianCalendar ate) {
        removerHora(de);
        removerHora(ate);
        int result = 0;
        while (de.before(ate)) {
            de.add(GregorianCalendar.MONTH, 1);
            result++;
        }
        return result;
    }

    public static List<String> sequenciaAnoMes(String anoMesInicial, String anoMesFinal) {
        int anoInicial = DateUtils.getAno(anoMesInicial);
        int mesInicial = DateUtils.getMes(anoMesInicial);
        int anoFinal = DateUtils.getAno(anoMesFinal);
        int mesFinal = DateUtils.getMes(anoMesFinal);
        return sequenciaAnoMes(anoInicial, mesInicial, anoFinal, mesFinal);
    }

    public static List<String> sequenciaAnoMes(int anoInicial, int mesInicial, int anoFinal, int mesFinal) {
        List<String> sequencia = new ArrayList<String>();
        while (anoInicial < anoFinal || (anoInicial == anoFinal && mesInicial <= mesFinal)) {
            if (mesInicial < 10) {
                sequencia.add(String.valueOf(anoInicial).concat("0").concat(String.valueOf(mesInicial)));
            } else {
                sequencia.add(String.valueOf(anoInicial).concat(String.valueOf(mesInicial)));
            }
            mesInicial++;
            if (mesInicial == 13) {
                mesInicial = 1;
                anoInicial++;
            }
        }
        return sequencia;
    }

    public static String getAnoMesFMT(String anomes) {
        return getAnoMesFMT(anomes, false);
    }

    public static String getAnoMesFMT(String anomes, boolean abreviado) {
        if (anomes != null && anomes.trim().length() == 6) {
            String mes = anomes.substring(4);
            try {
                mes = (abreviado ? getNomeMesAbrev(Integer.parseInt(mes)) : getNameMonthPt(Integer.parseInt(mes)));
            } catch (Exception ex) {
                //mantem o mes do substring
            }
            return mes + "/" + anomes.substring(0, 4);
        } else {
            return "";
        }
    }

    public static Date getDataHora(Date data, Date hora) {
        GregorianCalendar gc = null;
        GregorianCalendar gch = new GregorianCalendar();

        if (data == null || hora == null) {
            return null;
        }

        data = DateUtils.removeHour(data);
        gch.setTime(hora);

        gc = new GregorianCalendar();
        gc.setTime(data);
        gc.set(Calendar.HOUR_OF_DAY, gch.get(Calendar.HOUR_OF_DAY));
        gc.set(Calendar.MINUTE, gch.get(Calendar.MINUTE));
        gc.set(Calendar.SECOND, 0);
        gc.set(Calendar.MILLISECOND, 0);

        return gc.getTime();
    }

    public static String getMenorAnoMes(String anomes1, String anomes2) {
        if (anomes1 == null) {
            return anomes2;
        } else {
            if (anomes2 == null) {
                return anomes1;
            } else {
                Date d1 = primeiroDia(anomes1);
                Date d2 = primeiroDia(anomes2);
                if (d1.before(d2)) {
                    return anomes1;
                } else {
                    return anomes2;
                }
            }
        }
    }

    public static String getMaiorAnoMes(String anomes1, String anomes2) {
        if (anomes1 == null) {
            return anomes2;
        } else {
            if (anomes2 == null) {
                return anomes1;
            } else {
                Date d1 = primeiroDia(anomes1);
                Date d2 = primeiroDia(anomes2);
                if (d1.after(d2)) {
                    return anomes1;
                } else {
                    return anomes2;
                }
            }
        }
    }

    public static int getAno(String anomes) {
        if (anomes != null && anomes.trim().length() == 6) {
            return new Integer(anomes.substring(0, 4));
        }
        return 0;
    }

    public static int getMes(String anomes) {
        if (anomes != null && anomes.trim().length() == 6) {

            return new Integer(anomes.substring(4));
        } else {
            return 0;
        }
    }

    public static Date getAnoMesDiaToDate(String anoMesDia) {
        SimpleDateFormat sdfData = new SimpleDateFormat("yyyyMMdd");

        try {
            return sdfData.parse(anoMesDia);
        } catch (ParseException ex) {
            return null;
        }
    }

    public static String getDataAnoMesDia(Date data) {
        SimpleDateFormat sdfData = new SimpleDateFormat("yyyyMMdd");

        if (data == null) {
            return "00000000";
        } else {
            return sdfData.format(data);
        }
    }

    public static String getHoraMinuto(Date data) {
        SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm");

        if (data == null) {
            return "00:00";
        } else {
            return sdfHora.format(data);
        }
    }

    public static Date getHojeSemHora() {
        GregorianCalendar gc = new GregorianCalendar();
        gc.set(GregorianCalendar.HOUR_OF_DAY, 0);
        gc.set(GregorianCalendar.MINUTE, 0);
        gc.set(GregorianCalendar.SECOND, 0);
        gc.set(GregorianCalendar.MILLISECOND, 0);

        return gc.getTime();
    }

    public static String getTempoDecorrido(long milisegundos) {
        String tempo = "";
        long umDia = 24 * 60 * 60 * 1000;
        long umaHora = 60 * 60 * 1000;
        long umMinuto = 60 * 1000;

        long dias = 0;
        if (milisegundos >= umDia) {
            dias = milisegundos / umDia;
            milisegundos -= dias * umDia;
            tempo += dias + " dias, ";
        }

        long horas = 0;
        if (milisegundos >= umaHora) {
            horas = milisegundos / umaHora;
            milisegundos -= horas * umaHora;
            tempo += horas + " horas, ";
        }

        long minutos = 0;
        if (milisegundos >= umMinuto) {
            minutos = milisegundos / umMinuto;
            milisegundos -= minutos * umMinuto;
            tempo += minutos + " minutos e ";
        }

        long segundos = milisegundos / 1000;
        tempo += segundos + " segundos";

        return tempo;
    }

    public static boolean dataValida(String data) {
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        f.setLenient(false);
        try {
            f.parse(data);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    /**
     * Retorna quantos milisegundos existem de diferença entre duas horas
     *
     * @param de
     * @param ate
     * @return
     */
    public static long diferencaMilisegundos(Date de, Date ate) {
        long lDe = de.getTime();
        long lAte = ate.getTime();
        return lAte - lDe;
    }

    /**
     * Retorna quantos segundos existem de diferença entre duas horas
     *
     * @param de
     * @param ate
     * @return
     */
    public static long diferencaSegundos(Date de, Date ate) {
        long diferenca = diferencaMilisegundos(de, ate);
        return diferenca / 1000;
    }

    /**
     * Retorna quantos minutos existem de diferença entre duas horas
     *
     * @param de
     * @param ate
     * @return
     */
    public static long diferencaMinutos(Date de, Date ate) {
        long diferenca = diferencaSegundos(de, ate);
        return diferenca / 60;
    }

    /**
     * Retorna quantas horas existem de diferença entre duas horas
     *
     * @param de
     * @param ate
     * @return
     */
    public static long diferencaHoras(Date de, Date ate) {
        long diferenca = diferencaMinutos(de, ate);
        return diferenca / 60;
    }

    public static GregorianCalendar getGregorianCalendar(int ano, int mes, int dia) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.set(GregorianCalendar.YEAR, ano);
        gc.set(GregorianCalendar.MONTH, mes - 1);
        gc.set(GregorianCalendar.DAY_OF_MONTH, dia);
        gc.set(GregorianCalendar.HOUR, 0);
        gc.set(GregorianCalendar.MINUTE, 0);
        gc.set(GregorianCalendar.SECOND, 0);
        gc.set(GregorianCalendar.MILLISECOND, 0);
        return gc;
    }

    public static String adicionarAnoMes(String anomes, int meses) {
        if (anomes != null) {
            GregorianCalendar gc = new GregorianCalendar();
            int ano = Integer.parseInt(anomes.substring(0, 4));
            int mes = Integer.parseInt(anomes.substring(4)) - 1;

            gc.set(ano, mes, 1);
            gc.set(GregorianCalendar.HOUR_OF_DAY, 0);
            gc.set(GregorianCalendar.MINUTE, 0);
            gc.set(GregorianCalendar.SECOND, 0);
            gc.set(GregorianCalendar.MILLISECOND, 0);

            gc.add(GregorianCalendar.MONTH, meses);
            int novoAno = gc.get(GregorianCalendar.YEAR);
            int novoMes = gc.get(GregorianCalendar.MONTH) + 1;

            String result = Integer.toString(novoAno) + (novoMes < 10 ? "0" + novoMes : novoMes);
            return result;

        } else {
            return null;
        }

    }

    /**
     * Retorna true se <b>anomesA</b> for maior ou igual a <b>anomesB</b>.
     *
     * @param anomesA 'anomes' se null, retorna false
     * @param anomesB 'anomes' se null, retorna false
     * @return true/false
     */
    public static boolean isAnoMesMaiorOuIgual(String anomesA, String anomesB) {
        if (anomesA == null || anomesB == null) {
            return false;
        }
        return (Integer.parseInt(anomesA) >= Integer.parseInt(anomesB));
    }

    /**
     * Retorna true se <b>anomesA</b> for menor ou igual a <b>anomesB</b>.
     *
     * @param anomesA 'anomes' se null, retorna false
     * @param anomesB 'anomes' se null, retorna false
     * @return true/false
     */
    public static boolean isAnoMesMenorOuIgual(String anomesA, String anomesB) {
        if (anomesA == null || anomesB == null) {
            return false;
        }
        return (Integer.parseInt(anomesA) <= Integer.parseInt(anomesB));
    }

    public static Date localDateToDate(LocalDate d) {
        if (d == null) {
            return null;
        }
        return Date.from(d.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static boolean isAnoMesBetween(String anoMes, String anomesA, String anomesB) {
        if (FormatUtils.isEmpty(anoMes) || FormatUtils.isEmpty(anomesA) || FormatUtils.isEmpty(anomesB)) {
            return false;
        }
        int iAnoMes = Integer.parseInt(anoMes);
        int iAnomesA = Integer.parseInt(anomesA);
        int iAnomesB = Integer.parseInt(anomesB);
        return (iAnoMes >= iAnomesA && iAnoMes <= iAnomesB);
    }

    public static int getSemestre(String anoMes) {
        if (FormatUtils.isEmpty(anoMes) || !FormatUtils.isDigitos(anoMes)) {
            return 0;
        }
        int mes = getMes(anoMes);

        if (mes > 0 && mes < 7) {
            return 1;
        } else if (mes > 6 && mes < 13) {
            return 2;
        } else {
            return 0;
        }
    }

    public static Date getDateByAnoMesDia(String anoMesDia) {
        if (!FormatUtils.isDigitos(anoMesDia) || anoMesDia.length() != 8) {
            return null;
        }
        int ano = Integer.parseInt(anoMesDia.substring(0, 4));
        int mes = Integer.parseInt(anoMesDia.substring(4, 6));
        int dia = Integer.parseInt(anoMesDia.substring(6, 8));

        Date data = getDate(dia, mes, ano);

        if (DateUtils.getMonth(data) != mes
                || DateUtils.getYear(data) != ano
                || DateUtils.getDay(data) != dia) {
            return null;
        } else {
            return data;
        }
    }

    public static List<String> getAnosMesByAno(Integer ano) {
        if (ano == null || ano <= 0) {
            return null;
        }

        return Arrays.asList(
                ano + "01",
                ano + "02",
                ano + "03",
                ano + "04",
                ano + "05",
                ano + "06",
                ano + "07",
                ano + "08",
                ano + "09",
                ano + "10",
                ano + "11",
                ano + "12"
        );
    }

    /**
     * Retorna true se a data A for maior que a data B, desconsiderando horas.
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean isDataPosterior(Date a, Date b) {
        if (a == null || b == null) {
            return false;
        }
        a = DateUtils.removeHour(a);
        b = DateUtils.removeHour(b);
        return (a.after(b));
    }

    /**
     * Retorna true se a data A for maior que a data B + (dias), desconsiderando
     * horas.
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean isDataPosterior(Date a, Date b, int dias) {
        if (a == null || b == null) {
            return false;
        }

        a = DateUtils.removeHour(a);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(b);
        gc.set(GregorianCalendar.HOUR_OF_DAY, 0);
        gc.set(GregorianCalendar.MINUTE, 0);
        gc.set(GregorianCalendar.SECOND, 0);
        gc.set(GregorianCalendar.MILLISECOND, 0);
        gc.add(GregorianCalendar.DAY_OF_MONTH, dias);
        return (a.after(b));
    }

    public static Date getUltimaHora(Date dataFinal) {
        dataFinal = removeHour(dataFinal);
        dataFinal = DateUtils.adicionarHoras(dataFinal, 23);
        dataFinal = DateUtils.addMinutes(dataFinal, 59);
        dataFinal = DateUtils.addSeconds(dataFinal, 59);
        return dataFinal;
    }

    /**
     * Possibilita fazer requisição no backend entre microserviços passando
     * objeto do tipo Date na url/request;
     *
     * @param data
     * @return exemplo: "Mon Oct 15 2018 10:43:56"
     */
    public static String getDateForRequest(Date data) {
        if (data == null) {
            return null;
        }

        Calendar cal = Calendar.getInstance(Locale.US);
        SimpleDateFormat sdfHoraMinutoSegundo = new SimpleDateFormat("HH:mm:ss");
        cal.setTime(data);

        String dayWeek = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US);
        String month = cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US);
        String day = DateUtils.getDay(data) + "";
        String year = DateUtils.getYear(data) + "";
        String time = sdfHoraMinutoSegundo.format(data);

        String requestDate = dayWeek + " " + month + " " + day + " " + year + " " + time;
        return requestDate;

    }

    public static java.sql.Date getSQLDatefromUtilDate(java.util.Date data) {
        return data == null ? null : new java.sql.Date(data.getTime());
    }

    public static List<Integer> getListaDeUltimosAnos(int quantidade) {
        List<Integer> listaDeAnos = new ArrayList<>();

        int anoAtual = DateUtils.getAnoAtual();
        for (int i = anoAtual - quantidade; i <= anoAtual; i++) {
            listaDeAnos.add(i);
        }
        return listaDeAnos;
    }

    public static List<Integer> getListaDeMesesDoAno() {
        List<Integer> listaMeses = new ArrayList<>();

        for (int i = 1; i <= 12; i++) {
            listaMeses.add(i);
        }
        return listaMeses;
    }
}
