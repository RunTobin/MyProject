package mvp;

import java.util.ArrayList;
import java.util.List;

import com.linq.xinansmart.manager.EquipmentManager;
import com.linq.xinansmart.manager.LocationManager;
import com.linq.xinansmart.manager.Location_EquipmentManager;
import com.linq.xinansmart.model.Concenter;
import com.linq.xinansmart.model.Equipment;
import com.linq.xinansmart.model.Location;
import com.linq.xinansmart.model.Location_equipment;

public class AddLocation_Presenter {
	private I_AddLocation i_AddLocation;

	public AddLocation_Presenter(I_AddLocation i_AddLocation) {
		this.i_AddLocation = i_AddLocation;
	}

	public Location addLocation(LocationManager locationManager, String name,
			String image,String concenter) {
		Location location = locationManager.addLocation(name, image,concenter,"location");
		i_AddLocation.addLocation();
		return location;
	}

	public void addLocation_equiment(List<Location_equipment> equipments,
			Location_EquipmentManager location_EquipmentManager,
			Location location) {
		for (int i = 0; i < equipments.size(); i++) {
			location_EquipmentManager
					.addLocation_equipment(equipments.get(i).getX(), equipments
							.get(i).getY(), location, equipments.get(i)
							.getNcode(), equipments.get(i).getSvalue(),
							equipments.get(i).getType(), equipments.get(i)
									.getMachinID(), equipments.get(i)
									.getNindex(), equipments.get(i).getName(),
							equipments.get(i).getbOnline(),equipments.get(i).getEquCode());
		}
		i_AddLocation.addLocation_equiment();
	}
	private List<Equipment> eqList = new ArrayList<Equipment>();
	private CharSequence[] charSequences = new CharSequence[] {};
	
	public void getEqlist(Concenter concenter){
		eqList = EquipmentManager.getInstance().getAllEquipment(
				concenter.getUser(), concenter.getPassword());
		List<String> strings = new ArrayList<String>();
		for (Equipment eq : eqList) {
			strings.add(eq.getName());
		}
		charSequences = strings.toArray(charSequences);
		i_AddLocation.getEqlist_toArray(charSequences, eqList);
	}
}
