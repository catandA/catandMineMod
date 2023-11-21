package com.catand.catandminemod.functions;

import com.catand.catandminemod.Utils.LogUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ProfitCalculatorCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "cm";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/cm [timereset]";
    }

    @Override
    public List<String> getCommandAliases() {
        return new ArrayList<>();
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length != 1) {
            LogUtils.sendError("参数无效。 /cm [timereset]");
            return;
        }

        switch (args[0]) {
            case "timereset": {
                ProfitCalculator.reset();
                LogUtils.sendError("宝石计时器已重置");
                return;
            }
            default: {
                LogUtils.sendError("参数无效。 /cm [timereset]");
                break;
            }
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    @Override
    public int compareTo(@NotNull ICommand o) {
        return 0;
    }

    @Override
    public int getRequiredPermissionLevel() {
        return -1;
    }
}
