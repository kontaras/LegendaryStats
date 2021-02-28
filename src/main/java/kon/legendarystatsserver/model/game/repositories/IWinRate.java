package kon.legendarystatsserver.model.game.repositories;

public interface IWinRate {
	Integer getId();

	Integer getPlayed();

	Integer getWon();
	
	Integer getLost();
}