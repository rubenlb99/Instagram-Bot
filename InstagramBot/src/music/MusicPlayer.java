/**
 * 
 */
package music;

import java.io.FileInputStream;
import java.util.*;

import javazoom.jl.player.Player;

/**
 * @author ruben
 *
 */
public class MusicPlayer extends Thread {
	
	@Override
	public void run() {
		Random r = new Random();
		while(true) {
			List<Integer> nums = new ArrayList<Integer>();
			for(int i=1; i<=6; i++) {
				nums.add(i);
			}
			while(nums.size()>0) {
				int song = r.nextInt(nums.size());
				try {
			        FileInputStream fileInputStream = new FileInputStream(nums.get(song)+".mp3");
			   
			        Player player = new Player((fileInputStream));
			        player.play();
			        System.out.println("Song is playing");
			    }catch (Exception e){
			        System.out.println(e);
			    }
				nums.remove(song);
			}
		}
	}
	
	
	
}
