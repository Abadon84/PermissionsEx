package ru.tehkode.permission.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author code
 */
public class ConfigurationNode extends org.bukkit.util.config.ConfigurationNode {

    public ConfigurationNode(){
        this(new HashMap<String, Object>());
    }

    public ConfigurationNode(Map<String, Object> root) {
        super(root);
    }

    public Map<String, Object> getRoot() {
        return root;
    }

    /**
     * Gets a list of nodes. Non-valid entries will not be in the list.
     * There will be no null slots. If the list is not defined, the
     * default will be returned. 'null' can be passed for the default
     * and an empty list will be returned instead. The node must be
     * an actual node and cannot be just a boolean,
     *
     * @param path path to node (dot notation)
     * @param def default value or null for an empty list as default
     * @return list of integers
     */
    public List<ConfigurationNode> getNodesList(String path, List<ConfigurationNode> def) {
        List<Object> raw = getList(path);
        if (raw == null) {
            return def != null ? def : new ArrayList<ConfigurationNode>();
        }

        List<ConfigurationNode> list = new ArrayList<ConfigurationNode>();
        for (Object o : raw) {
            if (o instanceof Map) {
                list.add(new ConfigurationNode((Map<String, Object>)o));
            }
        }

        return list;
    }

    /**
     * Get a configuration node at a path. If the node doesn't exist or the
     * path does not lead to a node, null will be returned. A node has
     * key/value mappings.
     *
     * @param path
     * @return node or null
     */
    @Override
    public ConfigurationNode getNode(String path) {
        Object raw = getProperty(path);
        if (raw instanceof Map) {
            return new ConfigurationNode((Map<String, Object>)raw);
        }

        return null;
    }

    /**
     * Get a list of nodes at a location. If the map at the particular location
     * does not exist or it is not a map, null will be returned.
     *
     * @param path path to node (dot notation)
     * @return map of nodes
     */
    public Map<String, ConfigurationNode> getNodesMap(String path) {
        Object o = getProperty(path);
        if (o == null) {
            return null;
        } else if (o instanceof Map) {
            Map<String, ConfigurationNode> nodes =
                new HashMap<String, ConfigurationNode>();

            for (Map.Entry<String, Object> entry : ((Map<String, Object>)o).entrySet()) {
                if (entry.getValue() instanceof Map) {
                    nodes.put(entry.getKey(),
                            new ConfigurationNode((Map<String, Object>) entry.getValue()));
                }
            }

            return nodes;
        } else {
            return null;
        }
    }


    

}