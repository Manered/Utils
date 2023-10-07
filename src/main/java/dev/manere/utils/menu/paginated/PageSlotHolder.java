package dev.manere.utils.menu.paginated;

/**
 * The PageSlotHolder record represents a holder for a slot and page number in a menu system.
 * It is used to associate a specific slot on a page within a menu.
 */
public record PageSlotHolder(int slot, int page) {

    /**
     * Gets the slot number within a page.
     *
     * @return The slot number.
     */
    @Override
    public int slot() {
        return slot;
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
    @Override
    public int page() {
        return page;
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
