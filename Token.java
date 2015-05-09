public class Token{

	private Integer value;

	public Token(){
		value = null;
	}

	public Token(Integer player){
		value = player;
	}

	public Integer getToken(){
		return value;
	}
}