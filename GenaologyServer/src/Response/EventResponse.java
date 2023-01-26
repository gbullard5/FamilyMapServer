package Response;

import models.Event;

import java.util.ArrayList;

/**Response class for Event*/
public class EventResponse extends ParentResponse{
  private ArrayList<Event> data;

  public EventResponse(boolean success, String message, ArrayList<Event> data) {
    super(success, message);
    this.data=data;
  }

  public ArrayList<Event> getData() {
    return data;
  }

  public void setData(ArrayList<Event> data) {
    this.data=data;
  }
}

