public class Token{
/*this class reresent a token of an player*/
	private Integer value; // this indentify the owner of this token


	/*Constructor with a player*/
	public Token(Integer player){
		value = player;
	}
	/*Return the owner of this token*/
	public Integer getToken(){
		return value;
	}
}