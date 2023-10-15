package dev.manere.utils.menu;

import dev.manere.utils.item.ItemBuilder;
import org.bukkit.Material;

/**
 * The MenuButton class represents a button in a menu with an associated ItemBuilder and listener.
 */
public class MenuButton {
    private ItemBuilder item;
    private MenuButtonListener listener;
    private boolean isRefreshingButton;
    private boolean isRefreshingAsync;
    private long refreshDelay;
    private long refreshPeriod;

    /**
     * Constructs a new MenuButton with the specified ItemBuilder.
     */
    public MenuButton() {
        this.item = new ItemBuilder(Material.AIR);
        this.isRefreshingButton = false;
        this.refreshDelay = 0L;
        this.refreshPeriod = 20L;
        this.isRefreshingAsync = true;
    }

    /**
     * Creates a new MenuButton instance.
     *
     * @return A new MenuButton instance.
     */
    public static MenuButton of() {
        return new MenuButton();
    }

    /**
     * Creates a new MenuButton instance.
     *
     * @param item The ItemBuilder to use for the MenuButton.
     * @return A new MenuButton instance.
     */
    public static MenuButton of(ItemBuilder item) {
        return new MenuButton()
                .item(item);
    }

    /**
     * Creates a new MenuButton instance.
     *
     * @param item The ItemBuilder to use for the MenuButton.
     * @param listener The MenuButtonListener to use for the MenuButton.
     * @return A new MenuButton instance.
     */
    public static MenuButton of(ItemBuilder item, MenuButtonListener listener) {
        return new MenuButton()
                .item(item)
                .onClick(listener);
    }

    /**
     * Sets whether the button should refresh asynchronously.
     *
     * @param refreshAsync true if the button should refresh asynchronously, false otherwise
     * @return The MenuButton instance for method chaining
     */
    public MenuButton refreshAsync(boolean refreshAsync) {
        this.isRefreshingAsync = refreshAsync;
        return this;
    }

    /**
     * Sets whether the button should be refreshing.
     *
     * @param refresh true if the button should be refreshing, false otherwise
     * @return The MenuButton instance for method chaining
     */
    public MenuButton refresh(boolean refresh) {
        this.isRefreshingButton = refresh;
        return this;
    }

    /**
     * Sets the delay before the button refreshes.
     *
     * @param refreshDelay The delay in server ticks before the button refreshes
     * @return The MenuButton instance for method chaining
     */
    public MenuButton refreshDelay(long refreshDelay) {
        this.refreshDelay = refreshDelay;
        return this;
    }

    /**
     * Sets the period at which the button should refresh.
     *
     * @param refreshPeriod The period in server ticks at which the button should refresh
     * @return The MenuButton instance for method chaining
     */
    public MenuButton refreshPeriod(long refreshPeriod) {
        this.refreshPeriod = refreshPeriod;
        return this;
    }

    /**
     * Sets both the delay and period for refreshing the button.
     *
     * @param refreshDelay The delay in server ticks before the button refreshes
     * @param refreshPeriod The period in server ticks at which the button should refresh
     * @return The MenuButton instance for method chaining
     */
    public MenuButton refreshTime(long refreshDelay, long refreshPeriod) {
        this.refreshDelay = refreshDelay;
        this.refreshPeriod = refreshPeriod;
        return this;
    }

    /**
     * Sets a listener for the MenuButton.
     *
     * @param listener The MenuButtonListener to set as the listener.
     * @return The MenuButton instance.
     */
    public MenuButton onClick(MenuButtonListener listener) {
        this.listener = listener;
        return this;
    }

    /**
     * Sets an ItemBuilder for the MenuButton.
     *
     * @param item The ItemBuilder to set as the item.
     * @return The MenuButton instance.
     */
    public MenuButton item(ItemBuilder item) {
        this.item = item;
        return this;
    }

    /**
     * Gets the ItemBuilder associated with the MenuButton.
     *
     * @return The ItemBuilder of the MenuButton.
     */
    public ItemBuilder getItem() {
        return item;
    }

    /**
     * Gets the listener associated with the MenuButton.
     *
     * @return The MenuButtonListener of the MenuButton.
     */
    public MenuButtonListener getListener() {
        return listener;
    }

    /**
     * Returns true if this button should refresh automatically.
     *
     * @return true if this button should refresh automatically.
     */
    public boolean isRefreshingButton() {
        return isRefreshingButton;
    }

    /**
     * Gets the refresh delay associated with the MenuButton.
     *
     * @return The refresh delay of the MenuButton.
     */
    public long getRefreshDelay() {
        return refreshDelay;
    }

    /**
     * Gets the refresh period associated with the MenuButton.
     *
     * @return The refresh period of the MenuButton.
     */
    public long getRefreshPeriod() {
        return refreshPeriod;
    }

    /**
     * Returns true if this button's refresh task should be run asynchronously.
     *
     * @return true if this button's refresh task should be run asynchronously.
     */
    public boolean isRefreshingAsync() {
        return isRefreshingAsync;
    }
}
