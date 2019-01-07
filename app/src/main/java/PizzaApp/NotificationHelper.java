package PizzaApp;


        import android.app.Notification;
        import android.app.NotificationChannel;
        import android.app.NotificationManager;
        import android.content.Context;
        import android.content.ContextWrapper;
        import android.graphics.Color;
        import android.os.Build;
        import android.support.annotation.RequiresApi;
        import android.support.v4.app.NotificationCompat;

        import com.example.reut.getpizza.R;

public class NotificationHelper extends ContextWrapper {

    private static final String CHANNEL_ID = "com.pizzaapp.pizzaapp.GETPIZZA";
    private static final String CHANNEL_NAME = "GetPizza";
    private NotificationManager nm;

    public NotificationHelper(Context base){
        super(base);
        createChannel();
    }

    private void createChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel nc = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            nc.enableLights(true);
            nc.enableVibration(true);
            nc.setLightColor(Color.GREEN);
            nc.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

            getManager().createNotificationChannel(nc);
        }
    }

    public NotificationManager getManager() {
        if(nm == null) nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        return nm;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification.Builder getChannelNotification(String title, String body){
        return new Notification.Builder(getApplicationContext(), CHANNEL_ID)
                .setContentText(body)
                .setContentTitle(title)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true);
    }
}
