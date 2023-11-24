package dev.manere.utils.menu;

import dev.manere.utils.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The Button class represents a button in a menu with an associated ItemBuilder and listener.
 */
public class Button {
    private ItemBuilder item;
    private ButtonListener listener;
    private boolean isRefreshingButton;
    private boolean isRefreshingAsync;
    private long refreshDelay;
    private long refreshPeriod;

    /**
     * Constructs a new Button with the specified ItemBuilder.
     */
    public Button() {
        this.item = new ItemBuilder(Material.AIR);
        this.isRefreshingButton = false;
        this.refreshDelay = 0L;
        this.refreshPeriod = 20L;
        this.isRefreshingAsync = true;
    }

    /**
     * Creates a new Button instance.
     *
     * @return A new Button instance.
     */
    public static @NotNull Button button() {
        return new Button();
    }

    /**
     * Creates a new Button instance.
     *
     * @param item The ItemBuilder to use for the Button.
     * @return A new Button instance.
     */
    public static @NotNull Button button(@Nullable ItemBuilder item) {
        return new Button()
                .item(item);
    }

    /**
     * Creates a new Button instance.
     *
     * @param item The ItemBuilder to use for the Button.
     * @param listener The ButtonListener to use for the Button.
     * @return A new Button instance.
     */
    public static @NotNull Button button(@Nullable ItemBuilder item, @Nullable ButtonListener listener) {
        return new Button()
                .item(item)
                .onClick(listener);
    }

    /**
     * Sets whether the button should refresh asynchronously.
     *
     * @param refreshAsync true if the button should refresh asynchronously, false otherwise
     * @return The Button instance for method chaining
     */
    public @NotNull Button refreshAsync(boolean refreshAsync) {
        this.isRefreshingAsync = refreshAsync;
        return this;
    }

    /**
     * Sets whether the button should be refreshing.
     *
     * @param refresh true if the button should be refreshing, false otherwise
     * @return The Button instance for method chaining
     */
    public @NotNull Button refresh(boolean refresh) {
        this.isRefreshingButton = refresh;
        return this;
    }

    /**
     * Sets the delay before the button refreshes.
     *
     * @param refreshDelay The delay in server ticks before the button refreshes
     * @return The Button instance for method chaining
     */
    public @NotNull Button refreshDelay(long refreshDelay) {
        this.refreshDelay = refreshDelay;
        return this;
    }

    /**
     * Sets the period at which the button should refresh.
     *
     * @param refreshPeriod The period in server ticks at which the button should refresh
     * @return The Button instance for method chaining
     */
    public @NotNull Button refreshPeriod(long refreshPeriod) {
        this.refreshPeriod = refreshPeriod;
        return this;
    }

    /**
     * Sets both the delay and period for refreshing the button.
     *
     * @param refreshDelay The delay in server ticks before the button refreshes
     * @param refreshPeriod The period in server ticks at which the button should refresh
     * @return The Button instance for method chaining
     */
    public @NotNull Button refreshTime(long refreshDelay, long refreshPeriod) {
        this.refreshDelay = refreshDelay;
        this.refreshPeriod = refreshPeriod;
        return this;
    }

    /**
     * Sets a listener for the Button.
     *
     * @param listener The ButtonListener to set as the listener.
     * @return The Button instance.
     */
    public @NotNull Button onClick(@Nullable ButtonListener listener) {
        this.listener = listener;
        return this;
    }

    /**
     * Sets an ItemBuilder for the Button.
     *
     * @param item The ItemBuilder to set as the item.
     * @return The Button instance.
     */
    public @NotNull Button item(@Nullable ItemBuilder item) {
        this.item = item;
        return this;
    }

    /**
     * Gets the ItemBuilder associated with the Button.
     *
     * @return The ItemBuilder of the Button.
     */
    public ItemBuilder item() {
        return item;
    }

    /**
     * Gets the listener associated with the Button.
     *
     * @return The ButtonListener of the Button.
     */
    public ButtonListener listener() {
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
     * Gets the refresh delay associated with the Button.
     *
     * @return The refresh delay of the Button.
     */
    public long refreshDelay() {
        return refreshDelay;
    }

    /**
     * Gets the refresh period associated with the Button.
     *
     * @return The refresh period of the Button.
     */
    public long refreshPeriod() {
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

    /**
     * An interface for handling click events on menu buttons.
     */
    public interface ButtonListener {
        /**
         * Called when a menu button is clicked.
         *
         * @param event The InventoryClickEvent associated with the button click.
         */
        void onClick(@NotNull InventoryClickEvent event);
    }
}
