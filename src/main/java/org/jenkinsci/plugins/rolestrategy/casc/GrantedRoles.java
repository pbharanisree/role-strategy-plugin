package org.jenkinsci.plugins.rolestrategy.casc;

import com.michelin.cio.hudson.plugins.rolestrategy.Role;
import com.michelin.cio.hudson.plugins.rolestrategy.RoleBasedAuthorizationStrategy;
import com.michelin.cio.hudson.plugins.rolestrategy.RoleMap;
import edu.umd.cs.findbugs.annotations.NonNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.kohsuke.accmod.Restricted;
import org.kohsuke.accmod.restrictions.NoExternalUse;
import org.kohsuke.stapler.DataBoundConstructor;

/**
 * Casc wrapper for Roles.
 *
 * @author <a href="mailto:nicolas.deloof@gmail.com">Nicolas De Loof</a>
 * @since 2.11
 */
@Restricted(NoExternalUse.class)
public class GrantedRoles {

  private final List<RoleDefinition> global;

  private final List<RoleDefinition> items;

  private final List<RoleDefinition> agents;

  /**
   * Create GrantedRoles.
   *
   * @param global List of global Roles
   * @param items  List of item Roles
   * @param agents List of agent Roles
   */
  @DataBoundConstructor
  public GrantedRoles(List<RoleDefinition> global, List<RoleDefinition> items, List<RoleDefinition> agents) {
    this.global = global;
    this.items = items;
    this.agents = agents;
  }

  protected Map<String, RoleMap> toMap() {
    Map<String, RoleMap> grantedRoles = new HashMap<>();
    if (global != null) {
      grantedRoles.put(RoleBasedAuthorizationStrategy.GLOBAL, retrieveRoleMap(global));
    }
    if (items != null) {
      grantedRoles.put(RoleBasedAuthorizationStrategy.PROJECT, retrieveRoleMap(items));
    }
    if (agents != null) {
      grantedRoles.put(RoleBasedAuthorizationStrategy.SLAVE, retrieveRoleMap(agents));
    }
    return grantedRoles;
  }

  @NonNull
  private RoleMap retrieveRoleMap(List<RoleDefinition> definitions) {
    TreeMap<Role, Set<String>> resMap = new TreeMap<>();
    for (RoleDefinition definition : definitions) {
      resMap.put(definition.getRole(), definition.getAssignments());
    }
    return new RoleMap(resMap);
  }

  public List<RoleDefinition> getGlobal() {
    return global;
  }

  public List<RoleDefinition> getItems() {
    return items;
  }

  public List<RoleDefinition> getAgents() {
    return agents;
  }
}
