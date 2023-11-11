package dev.manere.utils.menu.paginated;

/**
 * The PaginatedSlot record represents a holder for a slot and page number in a menu system.
 * It is used to associate a specific slot on a page within a menu.
 */
public record PaginatedSlot(int slot, int page) {
    public static PaginatedSlot paginatedSlot(int slot, int page) {
        return new PaginatedSlot(slot, page);
    }
}