package dev.manere.utils.menu.paginated;

/**
 * The PageSlotHolder class represents a holder for a slot and page number in a menu system.
 * It is used to associate a specific slot on a page within a menu.
 */
public class PageSlotHolder {

    public int slot;
    public int page;

    /**
     * Constructs a new PageSlotHolder with the specified slot and page.
     *
     * @param slot The slot number within a page.
     * @param page The page number.
     */
    public PageSlotHolder(int slot, int page) {
        this.slot = slot;
        this.page = page;
    }

    /**
     * Gets the slot number within a page.
     *
     * @return The slot number.
     */
    public int getSlot() {
        return slot;
    }

    /**
     * Gets the page number.
     *
     * @return The page number.
     */
    public int getPage() {
        return page;
    }
}
