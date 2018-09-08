import java.util.*;

public class CompareTaskModels implements Comparator<TaskModel>, java.util.Comparator<TaskModel> {
	
	public int compare(TaskModel t1, TaskModel t2) {
		String year = t1.getDueDate().substring(t1.getDueDate().length() -4);
		String secondYear = t2.getDueDate().substring(t2.getDueDate().length() -4);
		if(year.compareTo(secondYear) < 0) {
			return -1;
		} else if(year.compareTo(secondYear) > 0) {
			return 1; 
		}
		
		String month = t1.getDueDate().substring(0, 2);
		String secondMonth = t2.getDueDate().substring(0, 2);
		if(month.compareTo(secondMonth) < 0) {
			return -1;
		} else if(month.compareTo(secondMonth) > 0) {
			return 1; 
		}
		
		String day = t1.getDueDate().substring(3, 5);
		String secondDay = t2.getDueDate().substring(3, 5);
		return day.compareTo(secondDay);
	}
}
