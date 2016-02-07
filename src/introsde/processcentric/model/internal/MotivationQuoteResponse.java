package introsde.processcentric.model.internal;

public class MotivationQuoteResponse extends BasicResponse{

	private MotivationQuote result; 
	
	public MotivationQuote getResult() {
		return result;
	}

	public void setResult(MotivationQuote result) {
		this.result = result;
	}

	public MotivationQuoteResponse(){
		super();
	}
	
	public MotivationQuoteResponse(String message){
		super(message);
	}
	
	public class MotivationQuote {
		public String quote;
		public String author;
		
		public MotivationQuote(){
			
		}
	}
}
