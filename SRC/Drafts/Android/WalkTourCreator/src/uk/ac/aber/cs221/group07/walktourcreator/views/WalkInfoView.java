package uk.ac.aber.cs221.group07.walktourcreator.views;

import uk.ac.aber.cs221.group07.walktourcreator.R;
import uk.ac.aber.cs221.group07.walktourcreator.activities.GeneralActivity;
import uk.ac.aber.cs221.group07.walktourcreator.model.WalkModel;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * 
 * @author HarryBuckley
 *
 */
public class WalkInfoView extends PopupView{
	
	
	/**
	 * creates a WalkInfoView instance.
	 *  The WalkModel that is passed to it
	 *  is displayed in in the popup.
	 *  @param walk,
	 *  @param context,
	 */
	public WalkInfoView(GeneralActivity context,WalkModel walk){
		super(context);	
		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
	    View popupView = layoutInflater.inflate(R.layout.popup_finish_walk, null);  
		//super.setContentView(popupView);
	}

}