package cn.edu.sjtu.acm.jdbctaste.task.test;

import java.util.List;

import cn.edu.sjtu.acm.jdbctaste.Taste;
import cn.edu.sjtu.acm.jdbctaste.TasteTask;
import cn.edu.sjtu.acm.jdbctaste.dao.DaoFactory;
import cn.edu.sjtu.acm.jdbctaste.entity.Joke;
import cn.edu.sjtu.acm.jdbctaste.entity.Person;
import cn.edu.sjtu.acm.jdbctaste.task.CreateTablesTask;
import cn.edu.sjtu.acm.jdbctaste.task.DropTablesTask;

/**
 * This test case is to test whether you correctly update a joke
 * Test Methods: JokeDao.findJokesOfPerson, JokeDao.updateJoke, JokeDao.findJokesWithZanMoreThan
 * @author furtherlee
 *
 */
public class UpdateJokeTest extends CombinedTask{
	
	private class UpdateJokeTask implements TasteTask {

		private DaoFactory factory;

		public UpdateJokeTask(Taste taste) {
			this.factory = taste.getDaoFactory();
		}

		private void testFindJoke () {
			Person acm0 = factory.getPersonDao().findPersonByEmail("acm0@sjtu.edu.cn");
			
			List<Joke> jokesOfAcm0 = factory.getJokeDao().findJokesOfPerson(acm0);
			
			assertEqual (jokesOfAcm0.size(), 1);
			
			Joke joke = jokesOfAcm0.get(0);
			
			assertEqual (joke.getBody(), "hehe from person0");
		}
		
		private void testZan () {
			Joke joke = factory.getJokeDao().findJokeById(0);
			for (int i = 0; i < 100; i ++) joke.up();
			factory.getJokeDao().updateJoke(joke);
			
			List<Joke> fetchedJokes = factory.getJokeDao().findJokesWithZanMoreThan(50);
			assertEqual(fetchedJokes.size(), 1);
			
			Joke fetchedJoke = fetchedJokes.get(0);
			assertEqual(fetchedJoke.getBody(), joke.getBody());
		}
		
		@Override
		public boolean doit() {
			try {
				testFindJoke();
				testZan();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}
	}

	public UpdateJokeTest(Taste taste) {
		addTask(new CreateTablesTask(taste));
		addTask(new FillTableTask(taste));
		addTask(new UpdateJokeTask(taste));
		addTask(new DropTablesTask(taste));
	}
}
