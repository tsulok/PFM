package com.pinup.pfm.daogenerator;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;
import de.greenrobot.daogenerator.ToOne;

public class PFMDaoGenerator {

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "com.pinup.pfm.model.database");
        final Entity category = addCategory(schema);
        final Entity user = addUser(schema);
        final Entity transaction = addTransaction(schema, category);
        new DaoGenerator().generateAll(schema, "../mobile/src/main/java");
    }

    /**
     * Creates the entity of the category
     * @param schema The parent schema
     * @return The created entity
     */
    private static Entity addCategory(Schema schema) {
        Entity category = schema.addEntity("Category");
        category.implementsSerializable();

        category.addStringProperty("id").primaryKey();
        category.addStringProperty("serverId");

        category.addStringProperty("name");
        category.addIntProperty("order");
        category.addStringProperty("imageUri");

        /*** Relations ***/

        // Connect category to parent category ( 1 - 1 )
        Property categoryParentProperty = category.addStringProperty("parentCategoryId").getProperty();
        ToOne parentCategory = category.addToOne(category, categoryParentProperty);
        parentCategory.setName("parent");

        // Connect categoryParent to categories ( 1 - * )
        ToMany parentToCategories = category.addToMany(category, categoryParentProperty);
        parentToCategories.setName("children");

        return category;
    }

    /**
     * Creates the entity of the user
     * @param schema The parent schema
     * @return The entity of the user
     */
    private static Entity addUser(Schema schema) {
        Entity user = schema.addEntity("User");
        user.implementsSerializable();

        user.addStringProperty("id").primaryKey();
        user.addStringProperty("serverId");

        user.addStringProperty("name");
        user.addDateProperty("dob");

        return user;
    }

    /**
     * Creates the entity of the transaction
     * @param schema The parent schema
     * @param category The entity of the category
     * @return the entity of the transaction
     */
    private static Entity addTransaction(Schema schema, Entity category) {
        Entity transaction = schema.addEntity("Transaction");
        transaction.implementsSerializable();

        transaction.addStringProperty("id").primaryKey();
        transaction.addStringProperty("serverId");

        transaction.addDateProperty("date").notNull();
        transaction.addDoubleProperty("latitude");
        transaction.addDoubleProperty("longitude");
        transaction.addStringProperty("imageUri");
        transaction.addDoubleProperty("amount").notNull();
        transaction.addStringProperty("currency").notNull();
        transaction.addStringProperty("description");
        transaction.addStringProperty("tag");

        /*** Relations ***/
        // Connect transaction to a category ( 1 - 1 )
        Property transactionCategoryProperty = transaction.addStringProperty("categoryId").getProperty();
        ToOne parentCategory = transaction.addToOne(category, transactionCategoryProperty);
        parentCategory.setName("category");

        return transaction;
    }
}
