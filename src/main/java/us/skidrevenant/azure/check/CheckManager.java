package us.skidrevenant.azure.check;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sun.awt.util.IdentityLinkedList;
import us.skidrevenant.azure.check.checks.combat.aimassist.AimAssistA;
import us.skidrevenant.azure.check.checks.combat.autoclicker.AutoClickerA;
import us.skidrevenant.azure.check.checks.combat.aura.AuraA;
import us.skidrevenant.azure.check.checks.combat.aura.AuraB;
import us.skidrevenant.azure.check.checks.misc.PingSpoof;
import us.skidrevenant.azure.check.checks.misc.timer.Timer;
import us.skidrevenant.azure.check.checks.misc.badpackets.*;
import us.skidrevenant.azure.check.checks.misc.invtweaks.*;
import us.skidrevenant.azure.check.checks.misc.timer.TimerB;
import us.skidrevenant.azure.check.checks.motion.Fly;
import us.skidrevenant.azure.check.checks.motion.OmniSprint;
import us.skidrevenant.azure.check.checks.motion.Speed;
import us.skidrevenant.azure.check.checks.combat.velocity.VelocityA;
import us.skidrevenant.azure.stats.PlayerStats;

import java.util.Deque;

/**
 * @author SkidRevenant
 * at 28/05/2018
 */
@RequiredArgsConstructor
public class CheckManager {

    private final PlayerStats playerStats;

    @Getter
    private final Deque<Check> checks = new IdentityLinkedList<>();

    public void start() {
        addCheck(new AimAssistA(playerStats));

        addCheck(new AutoClickerA(playerStats));

        addCheck(new AuraA(playerStats));
        addCheck(new AuraB(playerStats));

        addCheck(new VelocityA(playerStats));

        addCheck(new BadPacketsA(playerStats));
        addCheck(new BadPacketsB(playerStats));
        addCheck(new BadPacketsC(playerStats));
        addCheck(new BadPacketsD(playerStats));
        addCheck(new BadPacketsE(playerStats));
        addCheck(new BadPacketsF(playerStats));
        addCheck(new BadPacketsG(playerStats));
        addCheck(new BadPacketsH(playerStats));
        addCheck(new BadPacketsI(playerStats));
        addCheck(new BadPacketsJ(playerStats));
        addCheck(new BadPacketsK(playerStats));
        addCheck(new BadPacketsL(playerStats));
        addCheck(new BadPacketsM(playerStats));

        addCheck(new InvTweaksA(playerStats));
        addCheck(new InvTweaksB(playerStats));
        addCheck(new InvTweaksC(playerStats));
        addCheck(new InvTweaksD(playerStats));
        addCheck(new InvTweaksE(playerStats));
        addCheck(new InvTweaksF(playerStats));
        addCheck(new InvTweaksG(playerStats));

        addCheck(new Timer(playerStats));
        addCheck(new TimerB(playerStats));
        addCheck(new PingSpoof(playerStats));

        addCheck(new Fly(playerStats));
        addCheck(new OmniSprint(playerStats));
        addCheck(new Speed(playerStats));
    }

    private void addCheck(Check check) {
        checks.addLast(check);
    }
}
