package com.echoes.model.combat;

/**
 * Immutable value object capturing the complete outcome of a single attack.
 *
 * <p>Decoupling the result from the strategy and the character model keeps
 * each class focused on a single responsibility: strategies calculate,
 * characters hold state, and {@code AttackResult} transfers data between
 * them and the view layer. This makes combat logic easy to test in isolation.</p>
 */
public final class AttackResult {

    private final int rawDamage;
    private final int netDamage;
    private final boolean critical;
    private final boolean miss;
    private final String description;

    /**
     * Constructs an attack result with all outcome fields.
     *
     * @param rawDamage   damage before any defence reduction (0 on a miss)
     * @param netDamage   damage after defence reduction, to apply to target HP
     * @param critical    whether this was a critical hit
     * @param miss        whether the attack missed entirely
     * @param description human-readable narrative of the attack for the view
     */
    public AttackResult(final int rawDamage,
                        final int netDamage,
                        final boolean critical,
                        final boolean miss,
                        final String description) {
        this.rawDamage = rawDamage;
        this.netDamage = miss ? 0 : netDamage;
        this.critical = critical;
        this.miss = miss;
        this.description = description;
    }

    /** @return raw damage before defence reduction */
    public int getRawDamage() { return rawDamage; }

    /** @return net damage to apply to the target's health */
    public int getNetDamage() { return netDamage; }

    /** @return true if this was a critical hit */
    public boolean isCritical() { return critical; }

    /** @return true if the attack missed the target */
    public boolean isMiss() { return miss; }

    /** @return human-readable description for display */
    public String getDescription() { return description; }
}
