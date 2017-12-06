package pzinsta.pizzeria.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.sql.DataSource;

import pzinsta.pizzeria.dao.PizzaDao;
import pzinsta.pizzeria.model.pizza.BakeStyle;
import pzinsta.pizzeria.model.pizza.Crust;
import pzinsta.pizzeria.model.pizza.CutStyle;
import pzinsta.pizzeria.model.pizza.Ingredient;
import pzinsta.pizzeria.model.pizza.IngredientType;
import pzinsta.pizzeria.model.pizza.Pizza;
import pzinsta.pizzeria.model.pizza.PizzaItem;
import pzinsta.pizzeria.model.pizza.PizzaSide;
import pzinsta.pizzeria.model.pizza.PizzaSize;
import pzinsta.pizzeria.util.DatasourceFactory;
import pzinsta.pizzeria.util.Utils;

public class PizzaDaoImpl implements PizzaDao {

	private DataSource dataSource = DatasourceFactory.getDataSource();
	private static final String SELECT_CRUSTS = "SELECT * FROM crust";
	private static final String SELECT_BAKE_STYLES = "SELECT * FROM bakestyle";
	private static final String SELECT_CUT_STYLES = "SELECT * FROM cutstyle";
	private static final String SELECT_INGREDIENT_TYPES = "SELECT * FROM ingredient_type";
	private static final String SELECT_INGREDIENTS = "SELECT * FROM ingredient";
	private static final String SELECT_PIZZA_SIZES = "SELECT * FROM pizzasize";
	private static final String SELECT_INGREDIENT_TYPE_NAME_BY_ID = "SELECT name FROM ingredient_type WHERE id = ?";
	private static final String SELECT_CRUST_BY_ID = "SELECT * FROM crust WHERE id = ?";
	private static final String SELECT_PIZZA_SIZE_BY_ID = "SELECT * FROM pizzasize WHERE id = ?";
	private static final String SELECT_BAKE_STYLE_BY_ID = "SELECT * FROM bakestyle WHERE id = ?";
	private static final String SELECT_CUT_STYLE_BY_ID = "SELECT * FROM cutstyle WHERE id = ?";
	private static final String INSERT_PIZZASIDE = "INSERT INTO pizzaside (name) VALUES (?)";
	private static final String INSERT_PIZZASIDE_INGREDIENT = "INSERT INTO pizzaside_ingredient (pizzaside_id, ingredient_id, quantity) VALUES (?, ?, ?)";
	private static final String INSERT_PIZZA = "INSERT INTO pizza (crust_id, pizzasize_id, left_pizzaside_id, right_pizzaside_id, bakestyle_id, cutstyle_id) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String SELECT_PIZZA_BY_ID = "SELECT * FROM pizza WHERE id = ?";
	private static final String SELECT_PIZZASIDE_BY_ID = "SELECT * FROM pizzaside WHERE id = ?";
	private static final String SELECT_INGREDIENT_IDS_AND_QUANTITIES_BY_PIZZASIDE_ID = "SELECT * FROM pizzaside_ingredient WHERE pizzaside_id = ?";
	private static final String SELECT_INGREDIENT_BY_ID = "SELECT * FROM ingredient WHERE id = ?";
	private static final String SELECT_INGREDIENT_TYPE_BY_ID = "SELECT * from ingredient_type WHERE id = ?";
	
	@Override
	public Set<Crust> getCrusts() {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CRUSTS)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			Set<Crust> crusts = new LinkedHashSet<>();
			while (resultSet.next()) {
				Crust crust = new Crust();
				crust.setId(resultSet.getLong("id"));
				crust.setName(resultSet.getString("name"));
				MonetaryAmount price = Monetary.getDefaultAmountFactory().setCurrency("USD")
						.setNumber(resultSet.getBigDecimal("price")).create();
				crust.setPrice(price);
				crusts.add(crust);
			}
			return crusts;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Set<BakeStyle> getBakeStyles() {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BAKE_STYLES)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			Set<BakeStyle> bakeStyles = new LinkedHashSet<>();
			while (resultSet.next()) {
				BakeStyle bakeStyle = new BakeStyle();
				bakeStyle.setId(resultSet.getLong("id"));
				bakeStyle.setName(resultSet.getString("name"));
				bakeStyles.add(bakeStyle);
			}
			return bakeStyles;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Set<CutStyle> getCutStyles() {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CUT_STYLES)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			Set<CutStyle> cutStyles = new LinkedHashSet<>();
			while (resultSet.next()) {
				CutStyle cutStyle = new CutStyle();
				cutStyle.setId(resultSet.getLong("id"));
				cutStyle.setName(resultSet.getString("name"));
				cutStyles.add(cutStyle);
			}
			return cutStyles;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Set<IngredientType> getIngredientTypes() {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_INGREDIENT_TYPES)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			Set<IngredientType> ingredientTypes = new LinkedHashSet<>();
			while (resultSet.next()) {
				IngredientType ingredientType = new IngredientType();
				ingredientType.setId(resultSet.getLong("id"));
				ingredientType.setName(resultSet.getString("name"));
				ingredientTypes.add(ingredientType);
			}
			return ingredientTypes;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Set<Ingredient> getIngredients() {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_INGREDIENTS)) {
			ResultSet resultSet = preparedStatement.executeQuery();

			Set<Ingredient> ingredients = new LinkedHashSet<>();

			while (resultSet.next()) {
				Ingredient ingredient = new Ingredient();

				ingredient.setId(resultSet.getLong("id"));

				ingredient.setName(resultSet.getString("name"));

				ingredient.setPrice(convertBigDecimalToMonetaryAmount(resultSet.getBigDecimal("price")));

				long ingredientTypeId = resultSet.getLong("ingredient_type_id");
				PreparedStatement selectIngredientTypeById = connection
						.prepareStatement(SELECT_INGREDIENT_TYPE_NAME_BY_ID);
				selectIngredientTypeById.setLong(1, ingredientTypeId);

				ResultSet ingredientTypeResultSet = selectIngredientTypeById.executeQuery();
				ingredientTypeResultSet.next();

				IngredientType ingredientType = new IngredientType();
				ingredientType.setId(ingredientTypeId);
				ingredientType.setName(ingredientTypeResultSet.getString("name"));
				ingredient.setType(ingredientType);

				ingredients.add(ingredient);
			}
			return ingredients;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private MonetaryAmount convertBigDecimalToMonetaryAmount(BigDecimal bigDecimalPrice) {
		return Monetary.getDefaultAmountFactory().setCurrency("USD").setNumber(bigDecimalPrice).create();
	}

	@Override
	public Set<PizzaSize> getPizzaSizes() {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PIZZA_SIZES)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			Set<PizzaSize> pizzaSizes = new LinkedHashSet<>();
			while (resultSet.next()) {
				PizzaSize pizzaSize = new PizzaSize();
				pizzaSize.setId(resultSet.getLong("id"));
				pizzaSize.setName(resultSet.getString("name"));
				pizzaSizes.add(pizzaSize);
			}
			return pizzaSizes;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Optional<Crust> getCrustById(long crustId) {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CRUST_BY_ID)) {
			preparedStatement.setLong(1, crustId);
			ResultSet resultSet = preparedStatement.executeQuery();
			Crust crust = null;
			if (resultSet.next()) {
				crust = new Crust();
				crust.setId(crustId);
				crust.setName(resultSet.getString("name"));
				crust.setPrice(convertBigDecimalToMonetaryAmount(resultSet.getBigDecimal("price")));
			}
			return Optional.ofNullable(crust);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Optional<PizzaSize> getPizzaSizeById(long pizzaSizeId) {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PIZZA_SIZE_BY_ID)) {
			preparedStatement.setLong(1, pizzaSizeId);
			ResultSet resultSet = preparedStatement.executeQuery();
			PizzaSize pizzaSize = null;
			if (resultSet.next()) {
				pizzaSize = new PizzaSize();
				pizzaSize.setId(pizzaSizeId);
				pizzaSize.setName(resultSet.getString("name"));
			}
			return Optional.ofNullable(pizzaSize);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Optional<BakeStyle> getBakeStyleById(long bakeStyleId) {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BAKE_STYLE_BY_ID)) {
			preparedStatement.setLong(1, bakeStyleId);
			ResultSet resultSet = preparedStatement.executeQuery();
			BakeStyle bakeStyle = null;
			if (resultSet.next()) {
				bakeStyle = new BakeStyle();
				bakeStyle.setId(bakeStyleId);
				bakeStyle.setName(resultSet.getString("name"));
			}
			return Optional.ofNullable(bakeStyle);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Optional<CutStyle> getCutStyleById(long cutStyleId) {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CUT_STYLE_BY_ID)) {
			preparedStatement.setLong(1, cutStyleId);
			ResultSet resultSet = preparedStatement.executeQuery();
			CutStyle cutStyle = null;
			if (resultSet.next()) {
				cutStyle = new CutStyle();
				cutStyle.setId(cutStyleId);
				cutStyle.setName(resultSet.getString("name"));
			}
			return Optional.ofNullable(cutStyle);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void savePizza(Pizza pizza) {
		try (Connection connection = dataSource.getConnection()) {
			connection.setAutoCommit(false);
			try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PIZZASIDE,
					Statement.RETURN_GENERATED_KEYS)) {
				insertPizzaSide(preparedStatement, pizza.getLeft());
				insertPizzaSide(preparedStatement, pizza.getRight());

				PreparedStatement preparedStatement2 = connection.prepareStatement(INSERT_PIZZASIDE_INGREDIENT);
				insertIntoPizzaSideIngredientLinkTable(pizza.getLeft(), preparedStatement2);
				insertIntoPizzaSideIngredientLinkTable(pizza.getRight(), preparedStatement2);
				
				insertPizza(pizza, connection);
			} catch (SQLException e) {
				connection.rollback();
				connection.setAutoCommit(true);
				throw e;
			}
			connection.commit();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private void insertPizza(Pizza pizza, Connection connection) throws SQLException {
		PreparedStatement preparedStatement3 = connection.prepareStatement(INSERT_PIZZA, Statement.RETURN_GENERATED_KEYS);
		preparedStatement3.setLong(1, pizza.getCrust().getId());
		preparedStatement3.setLong(2, pizza.getSize().getId());
		preparedStatement3.setLong(3, pizza.getLeft().getId());
		preparedStatement3.setLong(4, pizza.getRight().getId());
		preparedStatement3.setLong(5, pizza.getBakeStyle().getId());
		preparedStatement3.setLong(6, pizza.getCutStyle().getId());
		preparedStatement3.executeUpdate();
		ResultSet pizzaKey = preparedStatement3.getGeneratedKeys();
		pizzaKey.next();
		pizza.setId(pizzaKey.getLong(1));
	}

	private void insertIntoPizzaSideIngredientLinkTable(PizzaSide pizzaSide, PreparedStatement preparedStatement2)
			throws SQLException {
		for (PizzaItem pizzaItem : pizzaSide.getItems()) {
			preparedStatement2.setLong(1, pizzaSide.getId());
			preparedStatement2.setLong(2, pizzaItem.getIngredient().getId());
			preparedStatement2.setLong(3, pizzaItem.getQuantity());
			preparedStatement2.executeUpdate();
		}
	}

	private void insertPizzaSide(PreparedStatement preparedStatement, PizzaSide leftSide) throws SQLException {
		preparedStatement.setString(1, leftSide.getName());
		preparedStatement.executeUpdate();
		ResultSet leftSideKey = preparedStatement.getGeneratedKeys();
		leftSideKey.next();
		leftSide.setId(leftSideKey.getLong(1));
	}

	@Override
	public Pizza getPizza(Long id) {
		Pizza pizza = new Pizza();
		pizza.setId(id);
		try (Connection connection = dataSource.getConnection()) {
			PreparedStatement selectPizzaByIdPreparedStatement = connection.prepareStatement(SELECT_PIZZA_BY_ID);
			selectPizzaByIdPreparedStatement.setLong(1, id);
			ResultSet resultSet = selectPizzaByIdPreparedStatement.executeQuery();
			resultSet.next();
			pizza.setBakeStyle(getBakeStyleById(resultSet.getLong("bakestyle_id")).orElse(null));
			pizza.setCrust(getCrustById(resultSet.getLong("crust_id")).orElse(null));
			pizza.setCutStyle(getCutStyleById(resultSet.getLong("cutstyle_id")).orElse(null));
			pizza.setSize(getPizzaSizeById(resultSet.getLong("pizzasize_id")).orElse(null));
			pizza.setLeft(getPizzaSideById(resultSet.getLong("left_pizzaside_id")));
			pizza.setRight(getPizzaSideById(resultSet.getLong("right_pizzaside_id")));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pizza;
	}
	
	private PizzaSide getPizzaSideById(Long id) {
		PizzaSide pizzaSide = new PizzaSide();
		pizzaSide.setId(id);
		try(Connection connection = dataSource.getConnection()) {
			PreparedStatement selectPizzaSideByIdPreparedStatement = connection.prepareStatement(SELECT_PIZZASIDE_BY_ID);
			selectPizzaSideByIdPreparedStatement.setLong(1, id);
			ResultSet resultSet = selectPizzaSideByIdPreparedStatement.executeQuery();
			resultSet.next();
			pizzaSide.setName(resultSet.getString("name"));
			pizzaSide.setItems(getPizzaItemsByPizzaSideId(id));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pizzaSide;
	}
	
	private Collection<PizzaItem> getPizzaItemsByPizzaSideId(Long id) {
		Collection<PizzaItem> pizzaItems = new LinkedHashSet<>();
		try(Connection connection = dataSource.getConnection()) {
			PreparedStatement selectIngredientIdsAndQuantitiesByPizzaSideIdPrepareStatement = connection.prepareStatement(SELECT_INGREDIENT_IDS_AND_QUANTITIES_BY_PIZZASIDE_ID);
			selectIngredientIdsAndQuantitiesByPizzaSideIdPrepareStatement.setLong(1, id);
			ResultSet resultSet = selectIngredientIdsAndQuantitiesByPizzaSideIdPrepareStatement.executeQuery();
			while (resultSet.next()) {
				PizzaItem pizzaItem = new PizzaItem();
				pizzaItem.setQuantity(resultSet.getInt("quantity"));
				pizzaItem.setIngredient(getIngredientById(resultSet.getLong("ingredient_id")));
				pizzaItems.add(pizzaItem);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pizzaItems;
	}
	
	private Ingredient getIngredientById(Long id) {
		Ingredient ingredient = new Ingredient();
		ingredient.setId(id);
		try(Connection connection = dataSource.getConnection()) {
			PreparedStatement selectIngredientByIdPreparedStatement = connection.prepareStatement(SELECT_INGREDIENT_BY_ID);
			selectIngredientByIdPreparedStatement.setLong(1, id);
			ResultSet resultSet = selectIngredientByIdPreparedStatement.executeQuery();
			resultSet.next();
			ingredient.setName(resultSet.getString("name"));
			ingredient.setPrice(Utils.fromBigDecimal(resultSet.getBigDecimal("price")));
			ingredient.setType(getIngredientTypeById(resultSet.getLong("ingredient_type_id")));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ingredient;
	}
	
	private IngredientType getIngredientTypeById(Long id) {
		IngredientType ingredientType = new IngredientType();
		ingredientType.setId(id);
		try(Connection connection = dataSource.getConnection()) {
			PreparedStatement selectIngredientTypeById = connection.prepareStatement(SELECT_INGREDIENT_TYPE_BY_ID);
			selectIngredientTypeById.setLong(1, id);
			ResultSet resultSet = selectIngredientTypeById.executeQuery();
			resultSet.next();
			ingredientType.setName(resultSet.getString("name"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ingredientType;
	}

}
