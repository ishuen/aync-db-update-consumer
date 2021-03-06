public class TradeProcessor {
  private static final TradeDao tradeDao = new TradeDao();
  public void saveRecord(String tradeString) throws Exception {
    // [{"T":"t","S":"AAPL","i":2195,"x":"V","p":143.42,"s":100,"c":["@"],"z":"C","t":"2021-10-08T15:13:03.13396615Z"}]
    TradeRecord[] record = JsonConverter.toObject(tradeString, TradeRecord[].class);
    // save record to DB
    tradeDao.save(record[0]);
  }
}
