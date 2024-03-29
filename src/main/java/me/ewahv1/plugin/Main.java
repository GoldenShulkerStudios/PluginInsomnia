package me.ewahv1.plugin;
import org.bukkit.plugin.java.JavaPlugin;
import me.ewahv1.plugin.Database.DatabaseConnection;
import me.ewahv1.plugin.Listeners.Difficulty.Items.FailTotemListener;
import me.ewahv1.plugin.Listeners.Difficulty.Storm.StormListener;
import me.ewahv1.plugin.Listeners.Items.TrinketBag.BagOfTrinkets;
import me.ewahv1.plugin.Commands.Difficulty.Storm.*;
import me.ewahv1.plugin.Commands.Difficulty.Totem.*;
import me.ewahv1.plugin.Listeners.Difficulty.Mobs.*;
import me.ewahv1.plugin.Listeners.Trinkets.*;

public class Main extends JavaPlugin {

    private DatabaseConnection connection;

    @Override
    public void onEnable() {
        connection = new DatabaseConnection();
        BagOfTrinkets bagOfTrinkets = new BagOfTrinkets(this);
        StormListener stormListener = new StormListener(this);
        FailTotemListener failTotemListener = new FailTotemListener(this);
        BeeListener beeListener = new BeeListener();
        BlazeListener blazeListener = new BlazeListener();
        CreeperListener creeperListener = new CreeperListener(this);
        DrownedListener drownedListener = new DrownedListener();
        ElderGuardianListener elderGuardianListener = new ElderGuardianListener();
        EndermanListener endermanListener = new EndermanListener();
        EndermiteListener endermiteListener = new EndermiteListener();
        GhastListener ghastListener = new GhastListener();
        GuardianListener guardianListener = new GuardianListener();
        HoglinListener hoglinListener = new HoglinListener();
        IronGolemListener ironGolemListener = new IronGolemListener();
        PhantomListener phantomListener = new PhantomListener();
        PiglinListener piglinListener = new PiglinListener();
        PillagerListener pillagerListener = new PillagerListener();
        RavagerListener ravagerListener = new RavagerListener();
        SilverfishListener silverfishListener = new SilverfishListener();
        SkeletonListener skeletonListener = new SkeletonListener();
        SlimeListener slimeListener = new SlimeListener();
        StrayListener strayListener = new StrayListener();
        VexListener vexListener = new VexListener();
        VindicatorListener vindicatorListener = new VindicatorListener();
        WitchListener witchListener = new WitchListener();
        WitherSkeletonListener witherSkeletonListener = new WitherSkeletonListener();
        ZoglinListener zoglinListener = new ZoglinListener();
        ZombieListener zombieListener = new ZombieListener();
        ZombieVillagerListener zombieVillagerListener = new ZombieVillagerListener();
        ZombifiedPiglinListener zombifiedPiglinListener = new ZombifiedPiglinListener();


        getServer().getPluginManager().registerEvents(new BagOfTrinkets(this), this);
        getServer().getPluginManager().registerEvents(stormListener, this);
        getServer().getPluginManager().registerEvents(failTotemListener, this);
        getServer().getPluginManager().registerEvents(beeListener, this); 
        getServer().getPluginManager().registerEvents(blazeListener, this); 
        getServer().getPluginManager().registerEvents(creeperListener, this);
        getServer().getPluginManager().registerEvents(drownedListener, this); 
        getServer().getPluginManager().registerEvents(elderGuardianListener, this); 
        getServer().getPluginManager().registerEvents(endermanListener, this); 
        getServer().getPluginManager().registerEvents(endermiteListener, this); 
        getServer().getPluginManager().registerEvents(ghastListener, this); 
        getServer().getPluginManager().registerEvents(guardianListener, this); 
        getServer().getPluginManager().registerEvents(hoglinListener, this); 
        getServer().getPluginManager().registerEvents(ironGolemListener, this); 
        getServer().getPluginManager().registerEvents(phantomListener, this); 
        getServer().getPluginManager().registerEvents(piglinListener, this); 
        getServer().getPluginManager().registerEvents(pillagerListener, this); 
        getServer().getPluginManager().registerEvents(ravagerListener, this); 
        getServer().getPluginManager().registerEvents(silverfishListener, this); 
        getServer().getPluginManager().registerEvents(skeletonListener, this); 
        getServer().getPluginManager().registerEvents(slimeListener, this); 
        getServer().getPluginManager().registerEvents(strayListener, this); 
        getServer().getPluginManager().registerEvents(vexListener, this); 
        getServer().getPluginManager().registerEvents(vindicatorListener, this); 
        getServer().getPluginManager().registerEvents(witchListener, this); 
        getServer().getPluginManager().registerEvents(witherSkeletonListener, this); 
        getServer().getPluginManager().registerEvents(zoglinListener, this); 
        getServer().getPluginManager().registerEvents(zombieListener, this); 
        getServer().getPluginManager().registerEvents(zombieVillagerListener, this); 
        getServer().getPluginManager().registerEvents(zombifiedPiglinListener, this); 

        getServer().getPluginManager().registerEvents(new ZombArmListener(), this);
        getServer().getPluginManager().registerEvents(new DescalcificadorListener(this, bagOfTrinkets), this);
        getServer().getPluginManager().registerEvents(new MADListener(), this);
        getServer().getPluginManager().registerEvents(new WarmogListener(this), this);


        getCommand("setstormtime").setExecutor(new SetStormTimeCommand(stormListener));
        getCommand("togglestorm").setExecutor(new ToggleStormCommand(stormListener));
        getCommand("resetstorm").setExecutor(new ResetStormCommand(stormListener));
        getCommand("stormstatus").setExecutor(new StormStatusCommand(stormListener));
        getCommand("setbasestormtime").setExecutor(new SetBaseStormTimeCommand(stormListener));

        getCommand("toggletotem").setExecutor(new ToggleTotemCommand(failTotemListener));
        getCommand("setfailtotem").setExecutor(new SetFailTotemCommand(failTotemListener));
        getCommand("totemstatus").setExecutor(new TotemStatusCommand(failTotemListener));

    }

    @Override
    public void onDisable() {
        connection.close();
    }
}
