<h3>Make a new event</h3>

<form class="form-stacked" action = "controllers/eventController.jsp" method = "GET">
     Event Name: <input type="text" name="eventName" />
     Description: <input type="text" name="description" />
     Location: <input type="text" name="location" />
     Start Date: <input type="text" id="datepicker" name="start" readonly="readonly" />
     End Date: <input type="text" id="datepicker2" name="end" readonly ="readonly" />
     <input class="btn primary" type="submit" name="submit" value="Make Event">
</form>
