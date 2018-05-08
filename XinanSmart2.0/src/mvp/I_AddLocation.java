package mvp;

import java.util.List;

import com.linq.xinansmart.model.Equipment;

public interface I_AddLocation {
	
	void addLocation();

	void addLocation_equiment();
	
	void getEqlist_toArray(CharSequence[] charSequences,List<Equipment> eqList);
}
