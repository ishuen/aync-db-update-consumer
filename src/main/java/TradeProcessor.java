public class TradeProcessor {
  public void saveRecord(String tradeString) throws Exception {
    TradeRecord record = JsonConverter.toObject(tradeString, TradeRecord.class);
    // save record to DB
  }
}
