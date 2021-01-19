package kon.legendarystatsserver.model.game;


public interface HeroesRepository extends ReadOnlyRepository<Hero, Integer> {

	Iterable<Hero> findAllBySet(Set set);
}
